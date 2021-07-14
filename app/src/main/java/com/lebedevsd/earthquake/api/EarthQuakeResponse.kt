package com.lebedevsd.earthquake.api

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * Rest representation of the response
 */
@JsonClass(generateAdapter = true)
data class EarthQuakeResponse(
    @Json(name = "earthquakes")
    val earthquakes: List<APIEarthQuake>
)

@JsonClass(generateAdapter = true)
data class APIEarthQuake(
    @Json(name = "datetime")
    val datetime: Date?,
    @Json(name = "depth")
    val depth: Float,
    @Json(name = "lng")
    val lng: Double,
    @Json(name = "src")
    val src: String,
    @Json(name = "eqid")
    val eqid: String,
    @Json(name = "magnitude")
    val magnitude: Float,
    @Json(name = "lat")
    val lat: Double
)
