package com.lebedevsd.earthquake.eqdetails

import com.lebedevsd.earthquake.InstantExecutorExtension
import com.lebedevsd.earthquake.RxSchedulerExtensionForJunit5
import com.lebedevsd.earthquake.util.DomainTestFixtures
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(InstantExecutorExtension::class, RxSchedulerExtensionForJunit5::class)
internal class EarthQuakeDetailsModelTest {

    private val earthQuake = DomainTestFixtures.createEarthQuake()
    private val viewModel: EarthQuakeDetailsViewModel = EarthQuakeDetailsViewModel(earthQuake)

    private val states = mutableListOf<EarthQuakeDetailsModel>()

    @BeforeEach
    fun init() {
        viewModel.states().observeForever { states.add(it) }
    }

    @Test
    fun `after initialize display the EarthQuake`() {
        viewModel.initialize()

        Assertions.assertThat(states.size).isEqualTo(1)
        Assertions.assertThat(states[0]).isEqualTo(EarthQuakeDetailsModel(earthQuake))
    }
}
