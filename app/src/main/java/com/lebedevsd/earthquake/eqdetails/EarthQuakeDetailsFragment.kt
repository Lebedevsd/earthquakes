package com.lebedevsd.earthquake.eqdetails

import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import android.view.View
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.lebedevsd.earthquake.R
import com.lebedevsd.earthquake.base.BaseFragment
import com.lebedevsd.earthquake.data.EarthQuake
import com.lebedevsd.earthquake.util.getViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@AndroidEntryPoint
class EarthQuakeDetailsFragment : BaseFragment(R.layout.earth_quake_details_fragment) {

    @Inject lateinit var viewModelFactory: EarthQuakeDetailsViewModelFactory
    private val viewModel: EarthQuakeDetailsViewModel by getViewModel {
        viewModelFactory.create(earthQuake).apply { this.initialize() }
    }
    private var earthQuakeDetailsView: EarthQuakeDetailsView? = null

    private val args: EarthQuakeDetailsFragmentArgs by navArgs()
    private val earthQuake: EarthQuake by lazy { args.params.earthQuake as EarthQuake }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBarWithBackButton(view.findViewById(R.id.toolbar))
        earthQuakeDetailsView = view.findViewById(R.id.earth_quake_details_view)

        earthQuakeDetailsView?.setListener(viewModel)
        print(earthQuake.toString())
        viewModel.states().observe(viewLifecycleOwner) {
            earthQuakeDetailsView?.render(it)
        }
    }

    override fun onDestroyView() {
        earthQuakeDetailsView = null
        super.onDestroyView()
    }

    @Keep
    @Parcelize
    data class Params(
        val earthQuake: EarthQuake
    ) : Parcelable {
        companion object {
            const val KEY = "params"
        }
    }
}
