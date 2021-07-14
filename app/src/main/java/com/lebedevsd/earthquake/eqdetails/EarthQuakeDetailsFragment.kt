package com.lebedevsd.earthquake.eqdetails

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.lebedevsd.earthquake.R
import com.lebedevsd.earthquake.api.APIEarthQuake
import com.lebedevsd.earthquake.util.getViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@AndroidEntryPoint
class EarthQuakeDetailsFragment : Fragment(R.layout.earth_quake_details_fragment) {

    @Inject lateinit var viewModelFactory: EarthQuakeDetailsViewModelFactory
    private val viewModel: EarthQuakeDetailsViewModel by getViewModel {viewModelFactory.create(earthQuake)}
    private var earthQuakeDetailsView: EarthQuakeDetailsView? = null

    private val args: EarthQuakeDetailsFragmentArgs by navArgs()
    private val earthQuake: APIEarthQuake by lazy { args.params.earthQuake as APIEarthQuake }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        earthQuakeDetailsView = view.findViewById(R.id.earth_quake_details_view)

        earthQuakeDetailsView?.setListener(viewModel)
        print(earthQuake.toString())
//        viewModel.states().observe(viewLifecycleOwner) {
//            earthQuakeDetailsView?.render(it)
//        }
//        viewModel.events().observe(viewLifecycleOwner) {
//
//        }
    }

    override fun onDestroyView() {
        earthQuakeDetailsView = null
        super.onDestroyView()
    }

    @Parcelize
    data class Params(
        val earthQuake: APIEarthQuake
    ) : Parcelable {
        companion object {
            const val KEY = "params"
        }
    }
}
