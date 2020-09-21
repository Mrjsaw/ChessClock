package com.example.android.chessclock


import android.R
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.TimePicker
import kotlinx.android.synthetic.main.fragment_settings.*
import java.util.*


class TimePickerFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val now = Calendar.getInstance()
        val mTimePicker = MyTimePickerDialog(this.context, object : MyTimePickerDialog.OnTimeSetListener {

            override fun onTimeSet(
                view: com.example.android.chessclock.TimePicker?,
                hourOfDay: Int,
                minute: Int,
                seconds: Int
            ) {
                top_time_s.text = "$minute:$seconds"
            }
        }, now[Calendar.HOUR_OF_DAY], now[Calendar.MINUTE], now[Calendar.SECOND], true)
        mTimePicker.setTitle("TopClock")
        return mTimePicker
    }

}

