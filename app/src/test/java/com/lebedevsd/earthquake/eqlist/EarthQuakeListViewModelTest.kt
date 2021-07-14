package com.lebedevsd.earthquake.eqlist

import com.lebedevsd.earthquake.InstantExecutorExtension
import com.lebedevsd.earthquake.RxSchedulerExtensionForJunit5
import com.lebedevsd.earthquake.data.NetworkError
import com.lebedevsd.earthquake.util.DomainTestFixtures
import com.lebedevsd.earthquake.util.Event
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(InstantExecutorExtension::class, RxSchedulerExtensionForJunit5::class)
internal class EarthQuakeListViewModelTest {

    private val useCase: GetEarthQuakesUseCase = mockk()
    private val viewModel: EarthQuakeListViewModel = EarthQuakeListViewModel(useCase)

    private val states = mutableListOf<EarthQuakeListModel>()
    private val events = mutableListOf<Event<EarthQuakeListEvent>>()

    @BeforeEach
    fun init() {
        clearMocks(useCase)
        viewModel.states().observeForever { states.add(it) }
        viewModel.events().observeForever { events.add(it) }
    }

    @Test
    fun `after initialize loads earthQuakes data`() {
        val loadedList = listOf(DomainTestFixtures.createEarthQuake())
        every { useCase.getEarthQuakes() } returns Single.just(Result.success(loadedList))

        viewModel.initialize()

        Assertions.assertThat(states.size).isEqualTo(2)
        Assertions.assertThat(states[0]).isEqualTo(EarthQuakeListModel.Loading)
        Assertions.assertThat((states[1] as EarthQuakeListModel.Data).value).isEqualTo(loadedList)
    }

    @Test
    fun `onRefresh loads earthQuakes data once again`() {
        val loadedList1 = listOf(DomainTestFixtures.createEarthQuake())
        val loadedList2 = listOf(DomainTestFixtures.createEarthQuake())
        every { useCase.getEarthQuakes() } returnsMany listOf(Single.just(Result.success(loadedList1)), Single.just(Result.success(loadedList2)))

        viewModel.initialize()
        viewModel.refreshItems()

        Assertions.assertThat(states.size).isEqualTo(4)
        Assertions.assertThat(states[0]).isEqualTo(EarthQuakeListModel.Loading)
        Assertions.assertThat((states[1] as EarthQuakeListModel.Data).value).isEqualTo(loadedList1)
        Assertions.assertThat(states[2]).isEqualTo(EarthQuakeListModel.Loading)
        Assertions.assertThat((states[3] as EarthQuakeListModel.Data).value).isEqualTo(loadedList2)
    }

    @Test
    fun `shows error when loading was not successful`() {
        val error = NetworkError()
        every { useCase.getEarthQuakes() } returns Single.just(Result.failure(error))

        viewModel.initialize()

        Assertions.assertThat(states.size).isEqualTo(2)
        Assertions.assertThat(states[0]).isEqualTo(EarthQuakeListModel.Loading)
        Assertions.assertThat(states[1] is EarthQuakeListModel.Error).isTrue()
    }

    @Test
    fun `navigationEvent is triggered when item is clicked`() {
        val loadedList = listOf(DomainTestFixtures.createEarthQuake())
        every { useCase.getEarthQuakes() } returns Single.just(Result.success(loadedList))

        viewModel.initialize()
        viewModel.onItemClicked(loadedList[0])

        Assertions.assertThat(events.size).isEqualTo(1)
        Assertions.assertThat((events[0] as Event<EarthQuakeListEvent.NavigateDetails>).getContentIfNotHandled()!!.earthQuake).isEqualTo(loadedList[0])
    }

}
