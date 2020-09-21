package com.example.android.chessclock


import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.TimePicker
import kotlinx.android.synthetic.main.fragment_settings.*
import java.util.*


class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val now = Calendar.getInstance()
        return MyTimePickerDialog(
            context,
            { _, hourOfDay, minute, seconds -> top_time.text = "$hourOfDay:$minute:$seconds" }, now[Calendar.HOUR_OF_DAY], now[Calendar.MINUTE], now[Calendar.SECOND], true)
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        // Do something with the time chosen by the user
        println("hi")
    }
}

