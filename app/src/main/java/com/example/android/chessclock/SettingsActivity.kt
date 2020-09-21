package com.example.android.chessclock

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.fragment_themes.*

class SettingsActivity : AppCompatActivity() {
    private lateinit var myPagerAdapter : MyFragmentPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
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
        TimePickerFragment().show(supportFragmentManager, "timePicker")
    }

    /**
     * Return to MainActivity
     */
    @Override
    override fun onBackPressed() {
        val openMainActivity = Intent(this, MainActivity::class.java)
        openMainActivity.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
        startActivityIfNeeded(openMainActivity, 0);
    }
}