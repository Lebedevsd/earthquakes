package com.lebedevsd.earthquake.eqlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.lebedevsd.earthquake.R
import com.lebedevsd.earthquake.api.APIEarthQuake
import com.lebedevsd.earthquake.eqdetails.EarthQuakeDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EarthQuakeListFragment : Fragment(R.layout.earth_quake_list_fragment) {

    private val viewModel: EarthQuakeListViewModel by viewModels()
    private var earthQuakeListView: EarthQuakeListView? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    private fun navigateDetails(earthQuake: APIEarthQuake) {
        val bundle = Bundle().apply {
            putParcelable(
                EarthQuakeDetailsFragment.Params.KEY,
                EarthQuakeDetailsFragment.Params(earthQuake)
            )
        }
        findNavController().navigate(R.id.earthQuakeDetailsFragment, bundle)
    }

    override fun onDestroyView() {
        earthQuakeListView = null
        super.onDestroyView()
    }
}
