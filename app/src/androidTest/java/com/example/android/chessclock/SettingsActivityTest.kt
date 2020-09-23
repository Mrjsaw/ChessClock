package com.example.android.chessclock

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsActivityTest {

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<SettingsActivity> =
        ActivityTestRule(SettingsActivity::class.java)

    @Test
    fun test_isActivityInView() {
        onView(withId(R.id.activitySettingsLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_banner() {
        onView(withId(R.id.settingsTitleIV)).check(matches(isDisplayed()))
        onView(withId(R.id.settingsTitleTV)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_tabs() {
        onView(withId(R.id.tabLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_content_caontainer() {
        onView(withId(R.id.viewPager)).check(matches(isDisplayed()))
    }
}