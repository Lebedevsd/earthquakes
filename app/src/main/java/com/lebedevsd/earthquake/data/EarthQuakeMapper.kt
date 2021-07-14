package com.lebedevsd.earthquake.data

import com.lebedevsd.earthquake.api.APIEarthQuake
import javax.inject.Inject


class EarthQuakeMapper
@Inject constructor() {

    fun map(apiEarthQuake: APIEarthQuake): EarthQuake {
        return EarthQuake(
            datetime = apiEarthQuake.datetime,
            depth = apiEarthQuake.depth,
            lng = apiEarthQuake.lng,
            src = apiEarthQuake.src,
            eqid = apiEarthQuake.eqid,
            magnitude = apiEarthQuake.magnitude,
            lat = apiEarthQuake.lat,
            isGreat = apiEarthQuake.magnitude >= GREAT_EARTH_QUAKE_THRESHOLD,
        )
    }

    companion object {
        private const val GREAT_EARTH_QUAKE_THRESHOLD = 8.0F
    }

}
