package com.example.android.chessclock

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    lateinit var countdown_timer: CountDownTimer
    var isRunning: Boolean = false
    var time_in_seconds = 900L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bot_sq.setOnClickListener {
            if (isRunning) {
                pauseTimer()
            } else {
                startTimer(time_in_seconds)
            }
        }


    }

    private fun pauseTimer() {
        countdown_timer.cancel()
        isRunning = false
    }

    private fun startTimer(tis: Long) {
        countdown_timer = object : CountDownTimer(tis*1000L, 1000) {
            override fun onFinish() {

            }

            override fun onTick(p0: Long) {
                time_in_seconds = p0 / 1000L
                updateTextUI()
            }
        }
        countdown_timer.start()

        isRunning = true

    }

    private fun updateTextUI() {
        bot_clock.text = Clock(time_in_seconds).updateText()
    }
}
