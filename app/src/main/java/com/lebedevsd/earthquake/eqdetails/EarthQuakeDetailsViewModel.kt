package com.lebedevsd.earthquake.eqdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lebedevsd.earthquake.api.APIEarthQuake
import com.lebedevsd.earthquake.data.EarthQuake
import com.lebedevsd.earthquake.util.Event
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject


class EarthQuakeDetailsViewModel
@AssistedInject constructor(
    @Assisted private val earthQuake: EarthQuake
) : ViewModel(), ViewContract {

    private val liveData = MutableLiveData<EarthQuakeDetailsModel>()

    fun states(): LiveData<EarthQuakeDetailsModel> = liveData

    fun initialize() {
        liveData.postValue(EarthQuakeDetailsModel(earthQuake))
    }

}

data class EarthQuakeDetailsModel(
    val earthQuake: EarthQuake
)

@AssistedFactory
interface EarthQuakeDetailsViewModelFactory {
    fun create(earthQuake: EarthQuake): EarthQuakeDetailsViewModel
}
