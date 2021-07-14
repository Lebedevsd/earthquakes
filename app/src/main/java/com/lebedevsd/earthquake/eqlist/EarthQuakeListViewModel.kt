package com.lebedevsd.earthquake.eqlist

import android.widget.TextView
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lebedevsd.earthquake.R
import com.lebedevsd.earthquake.api.APIEarthQuake
import com.lebedevsd.earthquake.util.Event
import dagger.assisted.AssistedFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class EarthQuakeListViewModel
@Inject constructor(
    getEarthQuakesUseCase: GetEarthQuakesUseCase
) : ViewModel(), ViewContract {

    private val compositeDisposable = CompositeDisposable()

    private val liveData = MutableLiveData<EarthQuakeListModel>()
    private val liveEvents = MutableLiveData<Event<EarthQuakeListEvent>>()

    fun states(): LiveData<EarthQuakeListModel> = liveData
    fun events(): LiveData<Event<EarthQuakeListEvent>> = liveEvents

    private val refreshSubject = BehaviorSubject.createDefault(Unit)

    init {
        compositeDisposable.addAll(
            refreshSubject.switchMap { getEarthQuakesUseCase.getEarthQuakes().toObservable() }
                .doOnSubscribe { liveData.postValue(EarthQuakeListModel.Loading) }
                .subscribe { data -> processResult(data) }
        )
    }

    private fun processResult(data: Result<List<APIEarthQuake>>) {
        liveData.postValue(
            if (data.isFailure) {
                EarthQuakeListModel.Error(R.string.error_loading_data)
            } else {
                EarthQuakeListModel.Data(data.getOrThrow())
            }
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    override fun refreshItems() {
        refreshSubject.onNext(Unit)
    }

    override fun onItemClicked(earthQuake: APIEarthQuake) {
        liveEvents.postValue(Event(EarthQuakeListEvent.NavigateDetails(earthQuake)))
    }
}

sealed class EarthQuakeListModel {
    object Loading : EarthQuakeListModel()
    data class Data(val value: List<APIEarthQuake>) : EarthQuakeListModel()
    data class Error(@StringRes val message: Int) : EarthQuakeListModel()
}

sealed class EarthQuakeListEvent {
    data class NavigateDetails(val earthQuake: APIEarthQuake) : EarthQuakeListEvent()
}
