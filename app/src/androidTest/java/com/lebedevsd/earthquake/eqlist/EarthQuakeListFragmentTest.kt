package com.lebedevsd.earthquake.eqlist

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lebedevsd.earthquake.MainActivity
import com.lebedevsd.earthquake.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class EarthQuakeListFragmentTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testNavigationToMapFragment() {
        onView(withId(R.id.swipe_to_refresh)).perform(ViewActions.swipeDown())

        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItem<EarthQuakeListView.EarthQuakeAdapter.ViewHolder>(
                hasDescendant(withText("7.8")),
                click()
            )
        )

        onView(withId(R.id.map_view)).check(matches(isDisplayed()))
    }
}
