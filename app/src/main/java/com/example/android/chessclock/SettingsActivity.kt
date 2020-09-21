package com.example.android.chessclock

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.fragment_settings.*
import java.util.*

class SettingsActivity : AppCompatActivity() {
    private lateinit var myPagerAdapter : MyFragmentPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_settings)

        myPagerAdapter = MyFragmentPagerAdapter(supportFragmentManager)
        viewPager.adapter = myPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        /*tabLayout.setTabTextColors(R.color.colorPrimary,R.color.colorAccent)
        tabLayout.setSelectedTabIndicator(R.color.colorAccent)*/
        tabLayout.getTabAt(0)?.text = "Game"
        tabLayout.getTabAt(1)?.text = "Theme"
    }

    fun showTimePickerDialog(v: View) {
        val now = Calendar.getInstance()
        val mTimePicker = MyTimePickerDialog(this,
            { _, _, minute, seconds ->
                var secs = seconds.toString()
                var mins = minute.toString()
                if(seconds < 10) {
                    secs = "0$seconds"
                }
                if(minute < 10) {
                    mins = "0$minute"
                }
                top_time_s.text = "$mins:$secs"

            }, now[Calendar.HOUR_OF_DAY], 15, 0, true) //verander deze waarden naar sharedPreference values
        mTimePicker.setTitle("Top Clock")
        mTimePicker.show()
    }
}