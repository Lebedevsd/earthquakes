package com.lebedevsd.earthquake.util

import com.lebedevsd.earthquake.data.EarthQuake
import java.util.*

internal object DomainTestFixtures {

    fun createEarthQuake(magnitude: Float = 8F, isGreat: Boolean = true) =
        EarthQuake(Date(), 1F, 30.0, "us", UUID.randomUUID().toString(), magnitude, 31.0, isGreat)
}
