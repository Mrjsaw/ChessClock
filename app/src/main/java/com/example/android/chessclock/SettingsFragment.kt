package com.example.android.chessclock

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker
import kotlinx.android.synthetic.main.fragment_settings.*
import java.util.*

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setTimePickerSpinners()
        super.onViewCreated(view, savedInstanceState)
    }
    private fun setTimePickerSpinners() {
        top_time_s.setOnClickListener {
            showTimePickerDialog(it, "Top Time")
        }
        bot_time_s.setOnClickListener {
            showTimePickerDialog(it, "Bottom Time")
        }
        top_inc_s.setOnClickListener {
            showTimePickerDialog(it, "Top Increment")
        }
        bot_inc_s.setOnClickListener {
            showTimePickerDialog(it, "Bottom Increment")
        }
    }
    private fun showTimePickerDialog(v: View, title: String) {
        val now = Calendar.getInstance()
        val mTimePicker = MyTimePickerDialog(activity,
            { _, _, minute, seconds ->
                var secs = seconds.toString()
                var mins = minute.toString()
                if(seconds < 10) {
                    secs = "0$seconds"
                }
                if(minute < 10) {
                    mins = "0$minute"
                }
                var tv = v as TextView
                tv.text = "$mins:$secs"

            }, now[Calendar.HOUR_OF_DAY], 15, 0, true) //verander deze waarden naar sharedPreference values
        mTimePicker.setTitle(title)
        mTimePicker.show()
    }
    companion object {
        fun newInstance(): SettingsFragment{
            return SettingsFragment()
        }
    }
}