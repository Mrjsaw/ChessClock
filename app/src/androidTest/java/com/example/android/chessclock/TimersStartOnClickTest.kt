package com.example.android.chessclock

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import android.view.View
import android.widget.TextView
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TimersStartOnClickTest {

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    private lateinit var startValue: String
    private lateinit var nextValue: String

    @Before
    fun setBotClockStatValues(){
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
                return isAssignableFrom(TextView::class.java)
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

    /**
     * Test bottom clock
     */
    @Test
    fun bot_clock_was_set_to_startvalue(){
        // Check if clock was set to startValue
        Espresso.onView(ViewMatchers.withId(R.id.bot_clock))
            .check(ViewAssertions.matches(ViewMatchers.withText(startValue)))
    }

    @Test
    fun user_can_start_bot_clock(){
        // Perform clicks
        Espresso.onView(ViewMatchers.withId(R.id.top_sq)).perform(ViewActions.click())

        // Check if clock updates
        Espresso.onView(ViewMatchers.withId(R.id.bot_clock))
            .check(ViewAssertions.matches(ViewMatchers.withText(nextValue)))
    }

    /**
     * Test top clock
     */
    @Test
    fun top_clock_was_set_to_startvalue(){
        // Check if clock was set to startValue
        Espresso.onView(ViewMatchers.withId(R.id.top_clock))
            .check(ViewAssertions.matches(ViewMatchers.withText(startValue)))
    }

    @Test
    fun user_can_start_top_clock(){
        // Perform clicks
        Espresso.onView(ViewMatchers.withId(R.id.bot_sq)).perform(ViewActions.click())

        // Check if clock updates
        Espresso.onView(ViewMatchers.withId(R.id.top_clock))
            .check(ViewAssertions.matches(ViewMatchers.withText(nextValue)))
    }
}