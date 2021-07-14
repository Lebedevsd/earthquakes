package com.lebedevsd.earthquake.eqdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lebedevsd.earthquake.api.APIEarthQuake
import com.lebedevsd.earthquake.util.Event
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject


class EarthQuakeDetailsViewModel
@AssistedInject constructor(
    @Assisted private val earthQuake: APIEarthQuake
) : ViewModel(), ViewContract {

    private val liveData = MutableLiveData<EarthQuakeDetailsModel>()
    private val liveEvents = MutableLiveData<Event<EarthQuakeDetailEvent>>()

    fun states(): LiveData<EarthQuakeDetailsModel> = liveData
    fun events(): LiveData<Event<EarthQuakeDetailEvent>> = liveEvents


}

class EarthQuakeDetailsModel()
class EarthQuakeDetailEvent()

@AssistedFactory
interface EarthQuakeDetailsViewModelFactory{
    fun create(earthQuake: APIEarthQuake): EarthQuakeDetailsViewModel
}
