package com.example.android.chessclock

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class MainActivityTest{

    @Rule @JvmField var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun test_isActivityInView() {
        onView(withId(R.id.dashBoard)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_buttons() {
        onView((withId(R.id.buttonBar))).check(matches(isDisplayed()))

        onView((withId(R.id.restartFrame))).check(matches(isDisplayed()))
        onView((withId(R.id.settingsFrame))).check(matches(isDisplayed()))
        onView((withId(R.id.pauseFrame))).check(matches(isDisplayed()))

        onView((withId(R.id.restart_button))).check(matches(isDisplayed()))
        onView((withId(R.id.settings_button))).check(matches(isDisplayed()))
        onView((withId(R.id.pause_button))).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun test_visibility_clocks() {
        onView((withId(R.id.top_sq))).check(matches(isDisplayed()))
        onView((withId(R.id.topClockContainer))).check(matches(isDisplayed()))
        onView((withId(R.id.top_clock))).check(matches(isDisplayed()))
        onView((withId(R.id.top_clock_mask))).check(matches(isDisplayed()))

        onView((withId(R.id.bot_sq))).check(matches(isDisplayed()))
        onView((withId(R.id.botClockContainer))).check(matches(isDisplayed()))
        onView((withId(R.id.bot_clock))).check(matches(isDisplayed()))
        onView((withId(R.id.bot_clock_mask))).check(matches(isDisplayed()))
    }
}