package com.lebedevsd.earthquake.data

import com.lebedevsd.earthquake.util.APITestFixtures
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class EarthQuakeMapperTest {

    private val mapper = EarthQuakeMapper()

    @Test
    fun `should convert API object to domain`() {
        val apiEarthQuake = APITestFixtures.createApiEarthQuake(10F)

        val converted = mapper.map(apiEarthQuake)

        Assertions.assertThat(converted).satisfies {
            it.eqid == apiEarthQuake.eqid &&
                    it.datetime == apiEarthQuake.datetime &&
                    it.depth == apiEarthQuake.depth &&
                    it.lat == apiEarthQuake.lat &&
                    it.lng == apiEarthQuake.lng &&
                    it.src == apiEarthQuake.src
        }
    }

    @Test
    fun `for magnitude greater then 8 should set boolean isGreat to true`() {
        val apiEarthQuake = APITestFixtures.createApiEarthQuake(10F)

        val converted = mapper.map(apiEarthQuake)

        Assertions.assertThat(converted.isGreat).isTrue
    }

    @Test
    fun `for magnitude equal 8 should set boolean isGreat to true`() {
        val apiEarthQuake = APITestFixtures.createApiEarthQuake(8F)

        val converted = mapper.map(apiEarthQuake)

        Assertions.assertThat(converted.isGreat).isTrue
    }

    @Test
    fun `for magnitude less then 8 should set boolean isGreat to true`() {
        val apiEarthQuake = APITestFixtures.createApiEarthQuake(7F)

        val converted = mapper.map(apiEarthQuake)

        Assertions.assertThat(converted.isGreat).isFalse
    }

}
