package com.lebedevsd.earthquake.eqlist

import com.lebedevsd.earthquake.data.EarthQuakeRepository
import com.lebedevsd.earthquake.di.IOScheduler
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject

/**
 * UseCase that provides list of [com.lebedevsd.earthquake.api.APIEarthQuake]'s
 */
class GetEarthQuakesUseCase
@Inject constructor(
    private val repository: EarthQuakeRepository,
    @IOScheduler private val ioScheduler: Scheduler
) {

    fun getEarthQuakes() = repository.getEarthQuakes()
        .subscribeOn(ioScheduler)
}
