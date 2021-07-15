package com.lebedevsd.earthquake.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

/**
 * Provides EarthQuake data from network API
 */
class EarthQuakeApi
@Inject
constructor(
    private val service: EarthQuakeRestApi
) {

    private val options = hashMapOf(
        EarthQuakeRestApi.FORMATTED to EarthQuakeRestApi.FORMATTED_DEFAULT,
        EarthQuakeRestApi.NORTH to EarthQuakeRestApi.NORTH_DEFAULT,
        EarthQuakeRestApi.SOUTH to EarthQuakeRestApi.SOUTH_DEFAULT,
        EarthQuakeRestApi.WEST to EarthQuakeRestApi.WEST_DEFAULT,
        EarthQuakeRestApi.EAST to EarthQuakeRestApi.EAST_DEFAULT
    )

    /**
     * Performs network call to get [List] of [APIEarthQuake]'s
     */
    fun getEarthQuakes(): Single<List<APIEarthQuake>> {
        return service.getEarthQuakes(options).map { it.earthquakes }
    }

}
