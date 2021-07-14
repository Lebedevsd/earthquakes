package com.lebedevsd.earthquake.eqdetails

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lebedevsd.earthquake.R
import com.lebedevsd.earthquake.data.EarthQuake
import com.lebedevsd.earthquake.databinding.EarthQuakeDetailsViewBinding
import com.lebedevsd.earthquake.databinding.EarthQuakeListViewBinding
import com.lebedevsd.earthquake.util.DateAdapter
import com.lebedevsd.earthquake.util.DateToStringUtil
import com.lebedevsd.earthquake.util.VerticalSpaceItemDecoration

class EarthQuakeDetailsView
@JvmOverloads
constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var listener: ViewContract = ViewContract.NULL
    private lateinit var binding: EarthQuakeDetailsViewBinding

    fun setListener(listener: ViewContract) {
        this.listener = listener
    }

    fun render(model: EarthQuakeDetailsModel) {
        showEarthQuakeOnMap(model.earthQuake)
    }

    private fun showEarthQuakeOnMap(earthQuake: EarthQuake) {
        binding.mapView.getMapAsync { map ->
            val coords = LatLng(earthQuake.lat, earthQuake.lng)
            map.addMarker(
                MarkerOptions()
                    .position(coords)
                    .title(resources.getString(R.string.eq_tooltip_info,
                        earthQuake.eqid,
                        earthQuake.magnitude.toString(),
                        earthQuake.datetime?.let { DateToStringUtil.dateToString(it) } ?: ""))
            )
            map.moveCamera(CameraUpdateFactory.newLatLng(coords))
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setupBinding()
    }

    private fun setupBinding() {
        binding = EarthQuakeDetailsViewBinding.bind(this)
        binding.mapView.onCreate(null)
        binding.mapView.onResume()
    }

    override fun onDetachedFromWindow() {
        this.listener = ViewContract.NULL
        binding.mapView.onPause()
        binding.mapView.onDestroy()
        super.onDetachedFromWindow()
    }
}

interface ViewContract {
    object NULL : ViewContract
}
