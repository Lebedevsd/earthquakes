package com.lebedevsd.earthquake.util

import com.lebedevsd.earthquake.api.APIEarthQuake
import java.util.*

internal object APITestFixtures {

    fun createApiEarthQuake(magnitude: Float = 8F) = APIEarthQuake(Date(), 1F, 30.0, "us", UUID.randomUUID().toString(), magnitude, 31.0)
}

