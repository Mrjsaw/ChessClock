package com.example.android.chessclock

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TimersStartOnClick {

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    private lateinit var startValue: String
    private lateinit var nextValue: String

    @Before
    fun setBotClockStatValues(){
        startValue = "15:00" // onView(withId(R.id.bot_clock)).toString()
        nextValue = "09:59"
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