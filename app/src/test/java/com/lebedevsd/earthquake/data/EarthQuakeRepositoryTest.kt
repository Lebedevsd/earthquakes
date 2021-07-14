package com.lebedevsd.earthquake.data

import com.lebedevsd.earthquake.api.EarthQuakeApi
import com.lebedevsd.earthquake.util.APITestFixtures
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class EarthQuakeRepositoryTest {
    private val api: EarthQuakeApi = mockk()
    private val mapper: EarthQuakeMapper = EarthQuakeMapper()
    private val repository = EarthQuakeRepository(api, mapper)

    @BeforeEach
    fun init() {
        clearMocks(api)
    }

    @Test
    fun `should return converted api objects to domain`() {
        val apiResult = listOf(APITestFixtures.createApiEarthQuake())

        every { api.getEarthQuakes() } returns Single.just(apiResult)

        val testObserver = repository.getEarthQuakes().test()

        testObserver.assertNoErrors()
        testObserver.assertValue {
            it.isSuccess && it.getOrThrow().size == apiResult.size
        }
    }

    @Test
    fun `should return NetworkError if there was an exception during request`() {
        val error = IllegalArgumentException()
        every { api.getEarthQuakes() } returns Single.error(error)

        val testObserver = repository.getEarthQuakes().test()

        testObserver.assertNoErrors()
        testObserver.assertValue {
            it.isFailure && it.exceptionOrNull()!! is NetworkError
        }
    }
}
