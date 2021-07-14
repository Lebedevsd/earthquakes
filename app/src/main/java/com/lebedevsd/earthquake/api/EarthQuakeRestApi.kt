package com.lebedevsd.earthquake.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Provides EarthQuakeData from network API
 */
interface EarthQuakeRestApi {

    companion object {
        const val endpoint = "http://api.geonames.org/"

        const val FORMATTED = "formatted"
        const val FORMATTED_DEFAULT = "true"
        const val NORTH = "north"
        const val NORTH_DEFAULT = "44.1"
        const val SOUTH = "south"
        const val SOUTH_DEFAULT = "-9.9"
        const val EAST = "east"
        const val EAST_DEFAULT = "-22.4"
        const val WEST = "west"
        const val WEST_DEFAULT = "55.2"
    }

    /**
     * Performs network call to get [EarthQuakeResponse]
     */
    @GET("earthquakesJSON")
    fun getEarthQuakes(@QueryMap params: Map<String, String>): Single<EarthQuakeResponse>
}
