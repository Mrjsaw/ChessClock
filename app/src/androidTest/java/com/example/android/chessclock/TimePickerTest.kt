package com.example.android.chessclock

import android.app.Instrumentation
import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.KeyEvent.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TimePickerTest {

    private val inst: Instrumentation = Instrumentation()

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java)

    // How to get string resources
    private fun getResourceString(id: Int): String? {
        val targetContext: Context = InstrumentationRegistry.getTargetContext()
        return targetContext.resources.getString(id)
    }

    @Test
    fun topClock_time_can_be_set() {
        onView(ViewMatchers.withId(R.id.settings_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.top_time_s)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.minute)).perform(ViewActions.click())
        inst.sendKeyDownUpSync(KEYCODE_5)
        onView(ViewMatchers.withText(getResourceString(R.string.setTime)))
            .perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.start_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.top_clock))
            .check(ViewAssertions.matches(ViewMatchers.withText("05:00")))

        onView(ViewMatchers.withId(R.id.settings_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.top_time_s)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.minute)).perform(ViewActions.click())
        inst.sendKeyDownUpSync(KEYCODE_1)
        inst.sendKeyDownUpSync(KEYCODE_2)
        onView(ViewMatchers.withText(getResourceString(R.string.setTime)))
            .perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.start_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.top_clock))
            .check(ViewAssertions.matches(ViewMatchers.withText("12:00")))

        onView(ViewMatchers.withId(R.id.settings_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.top_time_s)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.seconds)).perform(ViewActions.click())
        inst.sendKeyDownUpSync(KEYCODE_5)
        onView(ViewMatchers.withText(getResourceString(R.string.setTime)))
            .perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.start_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.top_clock))
            .check(ViewAssertions.matches(ViewMatchers.withText("12:05")))

        onView(ViewMatchers.withId(R.id.settings_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.top_time_s)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.seconds)).perform(ViewActions.click())
        inst.sendKeyDownUpSync(KEYCODE_1)
        inst.sendKeyDownUpSync(KEYCODE_2)
        onView(ViewMatchers.withText(getResourceString(R.string.setTime)))
            .perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.start_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.top_clock))
            .check(ViewAssertions.matches(ViewMatchers.withText("12:12")))

        onView(ViewMatchers.withId(R.id.settings_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.top_time_s)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.minute)).perform(ViewActions.click())
        inst.sendKeyDownUpSync(KEYCODE_1)
        inst.sendKeyDownUpSync(KEYCODE_0)
        onView(ViewMatchers.withId(R.id.seconds)).perform(ViewActions.click())
        inst.sendKeyDownUpSync(KEYCODE_0)
        onView(ViewMatchers.withText(getResourceString(R.string.setTime)))
            .perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.start_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.top_clock))
            .check(ViewAssertions.matches(ViewMatchers.withText("10:00")))
    }

    @Test
    fun botClock_time_can_be_set() {
        onView(ViewMatchers.withId(R.id.settings_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.bot_time_s)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.minute)).perform(ViewActions.click())
        inst.sendKeyDownUpSync(KEYCODE_5)
        onView(ViewMatchers.withText(getResourceString(R.string.setTime)))
            .perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.start_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.bot_clock))
            .check(ViewAssertions.matches(ViewMatchers.withText("05:00")))

        onView(ViewMatchers.withId(R.id.settings_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.bot_time_s)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.minute)).perform(ViewActions.click())
        inst.sendKeyDownUpSync(KEYCODE_1)
        inst.sendKeyDownUpSync(KEYCODE_2)
        onView(ViewMatchers.withText(getResourceString(R.string.setTime)))
            .perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.start_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.bot_clock))
            .check(ViewAssertions.matches(ViewMatchers.withText("12:00")))

        onView(ViewMatchers.withId(R.id.settings_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.bot_time_s)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.seconds)).perform(ViewActions.click())
        inst.sendKeyDownUpSync(KEYCODE_5)
        onView(ViewMatchers.withText(getResourceString(R.string.setTime)))
            .perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.start_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.bot_clock))
            .check(ViewAssertions.matches(ViewMatchers.withText("12:05")))

        onView(ViewMatchers.withId(R.id.settings_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.bot_time_s)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.seconds)).perform(ViewActions.click())
        inst.sendKeyDownUpSync(KEYCODE_1)
        inst.sendKeyDownUpSync(KEYCODE_2)
        onView(ViewMatchers.withText(getResourceString(R.string.setTime)))
            .perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.start_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.bot_clock))
            .check(ViewAssertions.matches(ViewMatchers.withText("12:12")))

        onView(ViewMatchers.withId(R.id.settings_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.bot_time_s)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.minute)).perform(ViewActions.click())
        inst.sendKeyDownUpSync(KEYCODE_1)
        inst.sendKeyDownUpSync(KEYCODE_0)
        onView(ViewMatchers.withId(R.id.seconds)).perform(ViewActions.click())
        inst.sendKeyDownUpSync(KEYCODE_0)
        onView(ViewMatchers.withText(getResourceString(R.string.setTime)))
            .perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.start_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.bot_clock))
            .check(ViewAssertions.matches(ViewMatchers.withText("10:00")))
    }
}