package com.example.android.chessclock

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    lateinit var countdown_timer_bot: CountDownTimer
    lateinit var countdown_timer_top: CountDownTimer
    var isRunningBot: Boolean = false
    var isRunningTop: Boolean = false
    var time_in_seconds_bot = 900L
    var time_in_seconds_top = 900L
    var increment = 5L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        top_sq.setOnClickListener {
            if (isRunningTop) {
                pauseTimerTop()
            } else {
                startTimerTop(time_in_seconds_top+increment)
            }

        }
        bot_sq.setOnClickListener {
            if (isRunningBot) {
                pauseTimerBot()
            } else {
                startTimerBot(time_in_seconds_bot+increment)
            }
        }


    }

    private fun pauseTimerBot() {
        countdown_timer_bot.cancel()
        isRunningBot = false
    }

    private fun pauseTimerTop() {
        countdown_timer_top.cancel()
        isRunningTop = false
    }

    private fun startTimerBot(seconds: Long) {
        countdown_timer_bot = object : CountDownTimer(seconds*1000L, 1000) {
            override fun onFinish() {
                Log.d("T","Bottom timer has ended!")
            }

            override fun onTick(p0: Long) {
                time_in_seconds_bot = p0 / 1000L
                updateTextUIBot()
            }
        }
        countdown_timer_bot.start()

        isRunningBot = true

    }

    private fun startTimerTop(seconds: Long) {
        countdown_timer_top = object: CountDownTimer(seconds*1000L,1000) {
            override fun onFinish() {
                Log.d("T","Top timer has ended!")
            }

            override fun onTick(p0: Long) {
                time_in_seconds_top = p0 / 1000L
                updateTextUITop()
            }
        }
        countdown_timer_top.start()

        isRunningTop = true

    }

    private fun updateTextUIBot() {
        bot_clock.text = Clock(time_in_seconds_bot).updateText()
    }
    private fun updateTextUITop() {
        top_clock.text = Clock(time_in_seconds_top).updateText()
    }
}

