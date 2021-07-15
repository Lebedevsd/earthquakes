package com.lebedevsd.earthquake.eqlist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.lebedevsd.earthquake.R
import com.lebedevsd.earthquake.api.APIEarthQuake
import com.lebedevsd.earthquake.base.BaseFragment
import com.lebedevsd.earthquake.data.EarthQuake
import com.lebedevsd.earthquake.eqdetails.EarthQuakeDetailsFragment
import com.lebedevsd.earthquake.eqdetails.EarthQuakeDetailsViewModel
import com.lebedevsd.earthquake.eqdetails.EarthQuakeDetailsViewModelFactory
import com.lebedevsd.earthquake.util.getViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EarthQuakeListFragment : BaseFragment(R.layout.earth_quake_list_fragment) {

    @Inject lateinit var viewModelFactory: EarthQuakeListViewModelFactory
    private val viewModel: EarthQuakeListViewModel by getViewModel {
        viewModelFactory.create().also { it.initialize() }
    }

    private var earthQuakeListView: EarthQuakeListView? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar(view.findViewById(R.id.toolbar))
        earthQuakeListView = view.findViewById(R.id.earth_quake_list_view)

        earthQuakeListView?.setListener(viewModel)
        viewModel.states().observe(viewLifecycleOwner) {
            earthQuakeListView?.render(it)
        }
        viewModel.events().observe(viewLifecycleOwner) {
            when (val event = it.getContentIfNotHandled()) {
                is EarthQuakeListEvent.NavigateDetails -> navigateDetails(event.earthQuake)
            }
        }
    }

    private fun navigateDetails(earthQuake: EarthQuake) {
        val bundle = Bundle().apply {
            putParcelable(
                EarthQuakeDetailsFragment.Params.KEY,
                EarthQuakeDetailsFragment.Params(earthQuake)
            )
        }
        findNavController().navigate(
            R.id.action_earthQuakeListFragment_to_earthQuakeDetailsFragment,
            bundle
        )
    }

    override fun onDestroyView() {
        earthQuakeListView = null
        super.onDestroyView()
    }
}
