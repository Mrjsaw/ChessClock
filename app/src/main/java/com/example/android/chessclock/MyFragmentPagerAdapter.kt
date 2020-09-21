package com.example.android.chessclock

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MyFragmentPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm){
    override fun getCount(): Int {
        return 2;
    }

    override fun getItem(p0: Int): Fragment? {
        return when(p0){
            0 -> SettingsFragment.newInstance()
            1 -> ThemesFragment.newInstance()
            else -> null
        }
    }
}