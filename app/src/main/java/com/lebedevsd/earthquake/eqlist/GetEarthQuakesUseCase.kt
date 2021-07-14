package com.lebedevsd.earthquake.eqlist

import com.lebedevsd.earthquake.data.EarthQuake
import com.lebedevsd.earthquake.data.EarthQuakeRepository
import com.lebedevsd.earthquake.di.IOScheduler
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * UseCase that provides list of [com.lebedevsd.earthquake.api.APIEarthQuake]'s
 */
class GetEarthQuakesUseCase
@Inject constructor(
    private val repository: EarthQuakeRepository,
    @IOScheduler private val ioScheduler: Scheduler
) {

    fun getEarthQuakes(): Single<Result<List<EarthQuake>>> = repository.getEarthQuakes()
        .subscribeOn(ioScheduler)
}
