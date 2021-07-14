package com.lebedevsd.earthquake.data

import com.lebedevsd.earthquake.api.APIEarthQuake
import com.lebedevsd.earthquake.api.EarthQuakeApi
import com.lebedevsd.earthquake.di.IOScheduler
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Repository for obtaining the EarthQuake data
 *
 * Could be extended to persist the data in local db
 */
class EarthQuakeRepository
@Inject constructor(
    private val restApi: EarthQuakeApi,
    private val mapper: EarthQuakeMapper
) {

    /**
     * @return [List] of [APIEarthQuake]
     */
    fun getEarthQuakes(): Single<Result<List<EarthQuake>>> = restApi.getEarthQuakes()
        .map { apiItemsList -> Result.success(apiItemsList.map { item -> mapper.map(item) }) }
        .onErrorReturn { Result.failure(NetworkError()) }
}
