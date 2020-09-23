package com.example.android.chessclock

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun test_navigation_to_settings() {
        onView(withId(R.id.settings_button)).perform(click())
        onView(withId(R.id.activitySettingsLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun test_backPress_toMainActivity() {
        onView(withId(R.id.settings_button)).perform(click())
        pressBack()
        onView(withId(R.id.dashBoard)).check(matches(isDisplayed()))
    }

    // How to get string resources
    private fun getResourceString(id: Int): String? {
        val targetContext: Context = InstrumentationRegistry.getTargetContext()
        return targetContext.resources.getString(id)
    }

    // Where to find tab id's
    @Test
    fun test_tabs_navigate_toFragments() {
        onView(withId(R.id.settings_button)).perform(click())
        onView(withText(getResourceString(R.string.themeStr))).perform(click())
        onView(withId(R.id.themeLayout)).check(matches(isDisplayed()))
        onView(withText(getResourceString(R.string.gameStr))).perform(click())
        onView(withId(R.id.fragmentSettingsLayoutFrame)).check(matches(isDisplayed()))
    }
}