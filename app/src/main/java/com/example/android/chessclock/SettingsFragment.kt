package com.example.android.chessclock

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_settings.*
import java.util.*

class SettingsFragment : Fragment() {

    private var sharedPreferences: SharedPreferences? = null
    private var sharedPrefsEdit: SharedPreferences.Editor? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences =
            this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
          sharedPrefsEdit = sharedPreferences?.edit()

        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        top_time_s.text = secondsToTime(sharedPreferences?.getInt("TOP_TIME", 900))
        top_inc_s.text = secondsToTime(sharedPreferences?.getInt("TOP_INC", 5))
        bot_time_s.text = secondsToTime(sharedPreferences?.getInt("BOT_TIME", 900))
        bot_inc_s.text = secondsToTime(sharedPreferences?.getInt("BOT_INC", 5))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTimePickerSpinners()

        start_button.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent)
            activity?.finish()
        }
    }


    private fun secondsToTime(timeSeconds: Int?): String {
        if (timeSeconds != null) {
            if (timeSeconds >= 60) {
                val mins = timeSeconds / 60
                val secs = timeSeconds % 60
                var minutes = mins.toString()
                var seconds = secs.toString()
                if (mins < 10) {
                    minutes = "0$minutes"
                }
                if (secs < 10) {
                    seconds = "0$seconds"
                }
                return "$minutes:$seconds"
            } else {
                if (timeSeconds < 10) {
                    return "00:0$timeSeconds"
                }
                return "00:$timeSeconds"
            }
        }
        return ""
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
        val tv = v as TextView
        val secondsTv = tv.text.split(':')[1].toInt()
        val minutesTv = tv.text.split(':')[0].toInt()
        val mTimePicker = MyTimePickerDialog(
            activity,
            { _, _, minute, seconds ->
                var secs = seconds.toString()
                var mins = minute.toString()
                if (seconds < 10) {
                    secs = "0$seconds"
                }
                if (minute < 10) {
                    mins = "0$minute"
                }
                val totalTimeInSeconds = mins.toInt() * 60 + secs.toInt()
                when (title) {
                    "Top Time" -> {
                        sharedPrefsEdit?.putInt("TOP_TIME", totalTimeInSeconds)
                        sharedPrefsEdit?.apply()
                    }
                    "Bottom Time" -> {
                        sharedPrefsEdit?.putInt("BOT_TIME", totalTimeInSeconds)
                        sharedPrefsEdit?.apply()
                    }
                    "Top Increment" -> {
                        sharedPrefsEdit?.putInt("TOP_INC", totalTimeInSeconds)
                        sharedPrefsEdit?.apply()
                    }
                    "Bottom Increment" -> {
                        sharedPrefsEdit?.putInt("BOT_INC", totalTimeInSeconds)
                        sharedPrefsEdit?.apply()
                    }
                }
                tv.text = getString(R.string.timeNotation, mins, secs)

            }, now[Calendar.HOUR_OF_DAY], minutesTv, secondsTv, true
        )
        mTimePicker.setTitle(title)
        mTimePicker.setOnShowListener {
            mTimePicker.getButton(MyTimePickerDialog.BUTTON_NEGATIVE)
                .setTextColor(resources.getColor(R.color.colorActive))
            mTimePicker.getButton(MyTimePickerDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.colorActive))
        }
        mTimePicker.show()

    }

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}