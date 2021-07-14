package com.lebedevsd.earthquake.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class EarthQuake(
    val datetime: Date?,
    val depth: Float,
    val lng: Double,
    val src: String,
    val eqid: String,
    val magnitude: Float,
    val lat: Double,
    val isGreat: Boolean
) : Parcelable
