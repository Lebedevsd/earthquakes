package com.lebedevsd.earthquake.eqlist

import com.lebedevsd.earthquake.data.EarthQuake
import com.lebedevsd.earthquake.data.EarthQuakeRepository
import com.lebedevsd.earthquake.data.NetworkError
import com.lebedevsd.earthquake.util.DomainTestFixtures
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GetEarthQuakesUseCaseTest{
    private val repository: EarthQuakeRepository = mockk()
    private val scheduler = Schedulers.trampoline()
    private val useCase = GetEarthQuakesUseCase(repository, scheduler)

    @BeforeEach
    fun init() {
        clearMocks(repository)
    }

    @Test
    fun `should return domain object if repo returned success`() {
        val result = Result.success(listOf(DomainTestFixtures.createEarthQuake()))

        every { repository.getEarthQuakes() } returns Single.just(result)

        val testObserver = useCase.getEarthQuakes().test()

        testObserver.assertNoErrors()
        testObserver.assertValue {
            it == result
        }
    }

    @Test
    fun `should return NetworkError if there was an exception in repository`() {
        val error : Result<List<EarthQuake>> = Result.failure(NetworkError())
        every { repository.getEarthQuakes() } returns Single.just(error)

        val testObserver = useCase.getEarthQuakes().test()

        testObserver.assertNoErrors()
        testObserver.assertValue {
            it == error
        }
    }
}
