package com.example.android.chessclock

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
         * Load sharedPreferenced & set up fragment
         */
        val action_theme = view.findViewById<Button>(R.id.action_theme)
        if (sharedPreferences != null) {
            isNightModeOn = sharedPreferences.getBoolean("BOOLEAN_KEY", false)
            action_theme.text = resources.getString(R.string.switchDark)
        }
        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            action_theme.text = resources.getString(R.string.switchLight)
        }

        /**
         * Switch between light and dark theme
         */
        action_theme.setOnClickListener {
            if (isNightModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPrefsEdit?.putBoolean("BOOLEAN_KEY", false)
                sharedPrefsEdit?.apply()
                isNightModeOn = false
                action_theme.text = resources.getString(R.string.switchLight)
                this.activity?.recreate()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPrefsEdit?.putBoolean("BOOLEAN_KEY", true)
                sharedPrefsEdit?.apply()
                isNightModeOn = true
                action_theme.text = resources.getString(R.string.switchDark)
                this.activity?.recreate()
            }
        }

        /**
         * Sound theme list
         */
        val spinner = view.findViewById<Spinner>(R.id.drop_down)
        val textView = view.findViewById<TextView>(R.id.drop_down_text)

        val sounds = arrayListOf("Select a sound...","Clear throat", "Mechanical click")

        spinner?.adapter = ArrayAdapter<String>(activity?.applicationContext, android.R.layout.simple_list_item_1, sounds)
        spinner?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
//                textView.text = "Select a sound..."
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                textView.text = sounds[position]
                //TODO: save value to sharedPrefs
                sharedPrefsEdit?.putString("TURN_SOUND", sounds[position])
                sharedPrefsEdit?.apply()
            }
        }

        return view
    }

    companion object {
        fun newInstance(): ThemesFragment{
            return ThemesFragment()
        }
    }
}