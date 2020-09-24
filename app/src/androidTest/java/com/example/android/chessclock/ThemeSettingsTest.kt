package com.example.android.chessclock

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withSpinnerText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import android.view.View
import android.widget.TextView
import org.hamcrest.CoreMatchers.anything
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ThemeSettingsTest {

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java)

    private lateinit var startValue: String
    private lateinit var nextValue: String

    @Before
    fun setBotClockStatValues() {
        startValue = getText(onView(withId(R.id.bot_clock)))
        val parts = startValue.split(":")
        var min = parts[0].toInt()
        var sec = parts[1].toInt()

        Log.d(min.toString(), sec.toString())

        val timeInSec = (min*60 + sec)-1

        min = timeInSec/60
        sec = timeInSec%60
        var minutes = min.toString()
        val seconds = sec.toString()

        if (minutes.length == 1){
            minutes = "0$minutes"
        }

        nextValue = "$minutes:$seconds"
    }

    private fun getText(matcher: ViewInteraction): String {
        var text = String()
        matcher.perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "Text of the view"
            }

            override fun perform(uiController: UiController, view: View) {
                val tv = view as TextView
                text = tv.text.toString()
            }
        })
        return text
    }

    // How to get string resources
    private fun getResourceString(id: Int): String? {
        val targetContext: Context = InstrumentationRegistry.getTargetContext()
        return targetContext.getResources().getString(id)
    }

    @Test
    fun test_selfStart_option_onTop() {
        Espresso.onView(ViewMatchers.withId(R.id.settings_button)).perform(click())
        Espresso.onView(ViewMatchers.withText(getResourceString(R.string.themeStr)))
            .perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.self_start_check)).perform(click())
        Espresso.pressBack()
        Espresso.onView(ViewMatchers.withId(R.id.top_sq)).perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.top_clock))
            .check(ViewAssertions.matches(ViewMatchers.withText(nextValue)))
        Espresso.onView(ViewMatchers.withId(R.id.settings_button)).perform(click())
        Espresso.onView(ViewMatchers.withText(getResourceString(R.string.themeStr)))
            .perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.self_start_check)).perform(click())
    }

    @Test
    fun test_selfStart_option_onBot() {
        Espresso.onView(ViewMatchers.withId(R.id.settings_button)).perform(click())
        Espresso.onView(ViewMatchers.withText(getResourceString(R.string.themeStr)))
            .perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.self_start_check)).perform(click())
        Espresso.pressBack()
        Espresso.onView(ViewMatchers.withId(R.id.bot_sq)).perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.bot_clock))
            .check(ViewAssertions.matches(ViewMatchers.withText(nextValue)))
        Espresso.onView(ViewMatchers.withId(R.id.settings_button)).perform(click())
        Espresso.onView(ViewMatchers.withText(getResourceString(R.string.themeStr)))
            .perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.self_start_check)).perform(click())
    }

    @Test
    fun test_dark_mode() {
        Espresso.onView(ViewMatchers.withId(R.id.settings_button)).perform(click())
        Espresso.onView(ViewMatchers.withText(getResourceString(R.string.themeStr)))
            .perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.action_theme)).perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.action_theme)).perform(click())
    }

    @Test
    fun test_sound_selection() {
        Espresso.onView(ViewMatchers.withId(R.id.settings_button)).perform(click())
        Espresso.onView(ViewMatchers.withText(getResourceString(R.string.themeStr)))
            .perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.drop_down)).perform(click())
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.drop_down)).check(
            matches(
                withSpinnerText(
                    containsString(
                        getResourceString(R.string.clearTroatStr)
                    )
                )
            )
        );
        Espresso.pressBack()
        Espresso.onView(ViewMatchers.withId(R.id.bot_sq)).perform(click())

        Espresso.onView(ViewMatchers.withId(R.id.settings_button)).perform(click())
        Espresso.onView(ViewMatchers.withText(getResourceString(R.string.themeStr)))
            .perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.drop_down)).perform(click())
        onData(anything()).atPosition(2).perform(click());
        onView(withId(R.id.drop_down)).check(
            matches(
                withSpinnerText(
                    containsString(
                        getResourceString(R.string.mechClickStr)
                    )
                )
            )
        );
        Espresso.pressBack()
        Espresso.onView(ViewMatchers.withId(R.id.top_sq)).perform(click())
    }
}