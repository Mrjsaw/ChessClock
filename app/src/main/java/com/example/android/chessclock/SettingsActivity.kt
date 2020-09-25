package com.example.android.chessclock


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_settings.*


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
        tabLayout.getTabAt(0)?.text = getString(R.string.gameStr)
        tabLayout.getTabAt(1)?.text = getString(R.string.themeStr)
    }

    /**
     * Return to MainActivity
     */
    @Override
    override fun onBackPressed() {
        val openMainActivity = Intent(this, MainActivity::class.java)
        openMainActivity.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
        startActivityIfNeeded(openMainActivity, 0);
        finish()
    }
}