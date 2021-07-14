package com.lebedevsd.earthquake.data

import com.lebedevsd.earthquake.api.APIEarthQuake
import com.lebedevsd.earthquake.api.EarthQuakeApi
import com.lebedevsd.earthquake.di.IOScheduler
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject

/**
 * Repository for obtaining the EarthQuake data
 *
 * Could be extended to persist the data in local db
 */
class EarthQuakeRepository
@Inject constructor(
    private val restApi: EarthQuakeApi
) {

    /**
     * @return [List] of [APIEarthQuake]
     */
    fun getEarthQuakes() = restApi.getEarthQuakes()
        .map { Result.success(it) }
        .onErrorReturn { Result.failure(NetworkError()) }
}
