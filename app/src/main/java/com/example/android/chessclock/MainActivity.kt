package com.example.android.chessclock

import android.content.Context
import android.content.SharedPreferences
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import android.view.View
import android.view.WindowManager

import kotlinx.android.synthetic.main.activity_main.*

const val START_TIME = 900 //change top/bot
const val INCREMENT = 5 //change top/bot

class MainActivity : AppCompatActivity() {

    enum class ClockStates { CLOCK_START, CLOCK_END }

    private lateinit var countDownTimerBot: CountDownTimer
    private lateinit var countDownTimerTop: CountDownTimer

    var clockState: ClockStates = ClockStates.CLOCK_START
    var time_in_seconds_bot = START_TIME
    var time_in_seconds_top = START_TIME
    private var isNightModeOn: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val sharedPrefsEdit:SharedPreferences.Editor=sharedPreferences.edit()

        loadData()

        action_theme.setOnClickListener(View.OnClickListener{
            if (isNightModeOn){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPrefsEdit.putBoolean("BOOLEAN_KEY",false)
                sharedPrefsEdit.apply()
                //TODO: redraw fragment without destroying somehow instead of using recreate
                recreate()

            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPrefsEdit.putBoolean("BOOLEAN_KEY",true)
                sharedPrefsEdit.apply()
                recreate()
            }
        })
        pause_button.setOnClickListener {
            pauseState()
        }
        restart_button.setOnClickListener {
            restartTimers()
        }
        settings_button.setOnClickListener {
            openSettings()
        }
        top_sq.setOnClickListener {

            when(clockState) {
                ClockStates.CLOCK_START -> startTimerBot(time_in_seconds_bot) //add increments hier nog
                ClockStates.CLOCK_END -> restartTimers()
            }

        }
        bot_sq.setOnClickListener {

            when(clockState) {
                ClockStates.CLOCK_START -> startTimerTop(time_in_seconds_top) //add increments hier nog
                ClockStates.CLOCK_END -> restartTimers()
            }
        }


    }

    private fun pauseState() {
        pauseTimerTop()
        pauseTimerBot()
        top_sq.isClickable=true
        bot_sq.isClickable=true
    }

    private fun restartTimers() {
        //add pop-up Are You Sure? [YES/NO]
        pauseTimerTop()
        pauseTimerBot()
        time_in_seconds_bot = START_TIME
        time_in_seconds_top = START_TIME
        updateTextUIBot()
        updateTextUITop()
        clockState = ClockStates.CLOCK_START
    }

    private fun pauseTimerBot() {
        if (this::countDownTimerBot.isInitialized) {
            countDownTimerBot.cancel()
        }
    }

    private fun pauseTimerTop() {
        if (this::countDownTimerTop.isInitialized) {
            countDownTimerTop.cancel()
        }
    }


    private fun startTimerBot(seconds: Int) {
        countDownTimerBot = object : CountDownTimer(seconds*1000L, 1000) {
            override fun onFinish() {
                Log.d("T","Bottom timer has ended!")
                clockState = ClockStates.CLOCK_END
            }

            override fun onTick(p0: Long) {
                time_in_seconds_bot = (p0 / 1000L).toInt()
                updateTextUIBot()
            }
        }
        countDownTimerBot.start()
        top_sq.setBackgroundColor(getColor(R.color.colorAccent))
        top_sq.isClickable=false
        bot_sq.isClickable=true
        pauseTimerTop()
        clockState = ClockStates.CLOCK_START
    }
    private fun openSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }
    private fun startTimerTop(seconds: Int) {
        countDownTimerTop = object: CountDownTimer(seconds*1000L,1000) {
            override fun onFinish() {
                Log.d("T","Top timer has ended!")
                clockState = ClockStates.CLOCK_END
            }

            override fun onTick(p0: Long) {
                time_in_seconds_top = (p0 / 1000L).toInt()
                updateTextUITop()
            }
        }
        countDownTimerTop.start()

        top_sq.isClickable=true
        bot_sq.isClickable=false
        pauseTimerBot()
        clockState = ClockStates.CLOCK_START

    }

    private fun updateTextUIBot() {
        bot_clock.text = Clock(time_in_seconds_bot).updateText()
    }
    private fun updateTextUITop() {
        top_clock.text = Clock(time_in_seconds_top).updateText()
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        isNightModeOn = sharedPreferences.getBoolean("BOOLEAN_KEY",false)

        if (isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}

