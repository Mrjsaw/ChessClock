package com.example.android.chessclock

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatDelegate
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_themes.*


class ThemesFragment : Fragment() {

    private var isNightModeOn: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_themes, container, false)

        // sharedPreferences
        val sharedPreferences = this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val sharedPrefsEdit: SharedPreferences.Editor? = sharedPreferences?.edit()

        /**
         * Set up fragment
         */
        loadData()

        /**
         * Switch between light and dark theme
         */
        val btn = view.findViewById<View>(R.id.action_theme) as Button
        btn.setOnClickListener {
            if (isNightModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPrefsEdit?.putBoolean("BOOLEAN_KEY", false)
                sharedPrefsEdit?.apply()
                isNightModeOn = false
                this.activity?.recreate()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPrefsEdit?.putBoolean("BOOLEAN_KEY", true)
                sharedPrefsEdit?.apply()
                isNightModeOn = true
                this.activity?.recreate()
            }
        }

        //TODO: Find a way to redraw activity_main on pressing 'Android return button'
        @Override
        fun onBackPressed() {

        }

        return view
    }

    /**
     * Load sharedPreferences
     */
    private fun loadData() {
        val sharedPreferences = this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        if (sharedPreferences != null) {
            isNightModeOn = sharedPreferences.getBoolean("BOOLEAN_KEY", false)
        }
        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    companion object {
        fun newInstance(): ThemesFragment{
            return ThemesFragment()
        }
    }
}