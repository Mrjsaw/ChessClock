package com.example.android.chessclock

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import android.view.View
import android.view.WindowManager

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    //TODO: Fix clock states ('CLOCKS_PAUSED', 'CLOCK_START' ,'BOT_START', 'TOP_START', 'BOT_END', 'TOP_END')


    enum class ClockStates { CLOCKS_PAUSED, CLOCK_INIT , CLOCK_START, CLOCK_END }

    private lateinit var countdown_timer_bot: CountDownTimer
    private lateinit var countdown_timer_top: CountDownTimer
    private var isRunningBot: Boolean = false
    private var isRunningTop: Boolean = false
    var clockState: ClockStates = ClockStates.CLOCK_INIT
    var time_in_seconds_bot = 900
    var time_in_seconds_top = 900
    var increment = 5

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

        top_sq.setOnClickListener {

            when(clockState) {
                ClockStates.CLOCK_INIT -> startTimerBot(time_in_seconds_bot, false) // maak top nu unclickable, bot clickable en zet state naar CLOCK START
                ClockStates.CLOCKS_PAUSED -> "paused" //
                ClockStates.CLOCK_START -> startTimerBot(time_in_seconds_bot,true) // pauzeer top clock en maak top unclickable en bot clickable en resume bot clock
                ClockStates.CLOCK_END -> "top start" // m
            }
            /*
            if (isRunningTop) {
                pauseTimerTop()
                top_sq.isClickable=false
                bot_sq.isClickable=true
                startTimerBot(time_in_seconds_bot)
            } else {
                startTimerTop(time_in_seconds_top)
            }
            */
            if (clockState == ClockStates.CLOCK_START) {

            }
        }
        bot_sq.setOnClickListener {
            /*
            if (isRunningBot) {
                pauseTimerBot()
                bot_sq.isClickable=false
                top_sq.isClickable=true
                startTimerTop(time_in_seconds_top)
            } else {
                startTimerBot(time_in_seconds_bot)
            }
             */
            when(clockState) {
                ClockStates.CLOCK_INIT -> startTimerTop(time_in_seconds_top,false) // maak top nu unclickable, bot clickable en zet state naar CLOCK START
                ClockStates.CLOCKS_PAUSED -> "paused" //
                ClockStates.CLOCK_START -> startTimerTop(time_in_seconds_top,true) // pauzeer top clock en maak top unclickable en bot clickable en resume bot clock
                ClockStates.CLOCK_END -> "top start" // m
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

    private fun startTimerBot(seconds: Int, check: Boolean) {
        countdown_timer_bot = object : CountDownTimer((seconds*1000).toLong(), 1000) {
            override fun onFinish() {
                Log.d("T","Bottom timer has ended!")
                clockState = ClockStates.CLOCK_END
                //TODO: Verander ClockState naar CLOCKS_END
            }

            override fun onTick(p0: Long) {
                time_in_seconds_bot = (p0 / 1000L).toInt()
                updateTextUIBot()
            }
        }
        countdown_timer_bot.start()

        isRunningBot = true
        top_sq.isClickable=false
        bot_sq.isClickable=true
        if(check) {
            pauseTimerTop()
        }

        clockState = ClockStates.CLOCK_START
    }

    private fun startTimerTop(seconds: Int, check: Boolean) {
        countdown_timer_top = object: CountDownTimer((seconds*1000).toLong(),1000) {
            override fun onFinish() {
                Log.d("T","Top timer has ended!")
                clockState = ClockStates.CLOCK_END
            }

            override fun onTick(p0: Long) {
                time_in_seconds_top = (p0 / 1000L).toInt()
                updateTextUITop()
            }
        }
        countdown_timer_top.start()

        isRunningTop = true
        top_sq.isClickable=true
        bot_sq.isClickable=false

        if (check) {
            pauseTimerBot()
        }

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

