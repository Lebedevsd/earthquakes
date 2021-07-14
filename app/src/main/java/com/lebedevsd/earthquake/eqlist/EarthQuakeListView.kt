package com.lebedevsd.earthquake.eqlist

import android.content.Context
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.text.bold
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.lebedevsd.earthquake.R
import com.lebedevsd.earthquake.api.APIEarthQuake
import com.lebedevsd.earthquake.data.EarthQuake
import com.lebedevsd.earthquake.databinding.EarthQuakeListItemBinding
import com.lebedevsd.earthquake.databinding.EarthQuakeListViewBinding
import com.lebedevsd.earthquake.util.DateToStringUtil
import com.lebedevsd.earthquake.util.VerticalSpaceItemDecoration


class EarthQuakeListView
@JvmOverloads
constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var binding: EarthQuakeListViewBinding
    private val adapter: EarthQuakeAdapter = EarthQuakeAdapter(this)
    private var listener: ViewContract = ViewContract.NULL

    init {
        adapter.setHasStableIds(true)
    }

    fun setListener(listener: ViewContract) {
        this.listener = listener
    }

    fun render(model: EarthQuakeListModel) {
        when (model) {
            is EarthQuakeListModel.Loading -> {
                showLoading()
            }
            is EarthQuakeListModel.Data -> {
                hideLoading()
                adapter.setEarthQuakes(model.value)
            }
            is EarthQuakeListModel.Error -> {
                hideLoading()
                showError(model)
            }
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setupBinding()
    }

    private fun setupBinding() {
        binding = EarthQuakeListViewBinding.bind(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.addItemDecoration(
            VerticalSpaceItemDecoration(
                R.dimen.space_small,
                context
            )
        )
        binding.swipeToRefresh.setOnRefreshListener { listener.refreshItems() }
    }

    override fun onDetachedFromWindow() {
        this.listener = ViewContract.NULL
        super.onDetachedFromWindow()
    }

    private fun showError(error: EarthQuakeListModel.Error) {
        Snackbar.make(binding.root, resources.getString(error.message), Snackbar.LENGTH_LONG).show()

    }

    private fun showLoading() {
        binding.swipeToRefresh.isRefreshing = true
    }

    private fun hideLoading() {
        binding.swipeToRefresh.isRefreshing = false
    }

    class EarthQuakeAdapter(private val view: EarthQuakeListView) :
        RecyclerView.Adapter<EarthQuakeAdapter.ViewHolder>() {
        private val earthQuakes: MutableList<EarthQuake> = mutableListOf()

        fun setEarthQuakes(earthQuakes: List<EarthQuake>) {
            this.earthQuakes.clear()
            this.earthQuakes.addAll(earthQuakes)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            position: Int
        ): ViewHolder {
            val itemBinding = EarthQuakeListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ViewHolder(itemBinding)
        }

        override fun getItemCount(): Int = earthQuakes.size

        override fun getItemId(position: Int): Long = earthQuakes[position].hashCode().toLong()

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(earthQuakes[position], view)
        }

        class ViewHolder internal constructor(private val itemBinding: EarthQuakeListItemBinding) :
            RecyclerView.ViewHolder(itemBinding.root) {
            fun bind(earthQuake: EarthQuake, view: EarthQuakeListView) {
                itemBinding.magnitudeValue.text = getMagnitude(earthQuake)
                itemBinding.dateValue.text =
                    earthQuake.datetime?.let { DateToStringUtil.dateToString(it) }
                itemBinding.root.setOnClickListener { view.listener.onItemClicked(earthQuake) }
            }

            private fun getMagnitude(earthQuake: EarthQuake) =
                if (earthQuake.isGreat) {
                    SpannableStringBuilder()
                        .bold { append(earthQuake.magnitude.toString()) }
                } else {
                    earthQuake.magnitude.toString()
                }
        }
    }
}

interface ViewContract {
    fun refreshItems()
    fun onItemClicked(earthQuake: EarthQuake)

    object NULL : ViewContract {
        override fun refreshItems() {
            // NO Implementation
        }

        override fun onItemClicked(earthQuake: EarthQuake) {
            // NO Implementation
        }
    }
}


