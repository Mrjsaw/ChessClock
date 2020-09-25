package com.example.android.chessclock

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    enum class ClockStates { CLOCK_START, CLOCK_END }

    var clockState: ClockStates? = null

    private lateinit var countDownTimerBot: CountDownTimer
    private lateinit var countDownTimerTop: CountDownTimer
    var tis = 500
    var timeInSecondsBot = 0
    var timeInSecondsTop = 0

    private var isNightModeOn: Boolean = false
    private var selfStart: Boolean = false
    private var gamePaused: Boolean = false

    private var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val sharedPrefsEdit: SharedPreferences.Editor = sharedPreferences.edit()
        timeInSecondsBot = sharedPreferences.getInt("BOT_TIME",900)
        timeInSecondsTop = sharedPreferences.getInt("TOP_TIME",900)
        top_clock.text = Clock(timeInSecondsTop).updateText()
        bot_clock.text = Clock(timeInSecondsBot).updateText()
        /**
         * Set up new session
         */
        loadData()
        pause_button.visibility = View.GONE

        /**
         * Button listeners
         */
        pause_button.setOnClickListener {
            pauseState()
        }

        restart_button.setOnClickListener {
            restartGame()
        }

        settings_button.setOnClickListener {
            openSettings()
        }

        /**
         * Clock listeners checking for 'selfStart' setting and starting caseLogic
         */
        top_sq.setOnClickListener {
            if (selfStart && clockState == null) {
                topCaseLogic()
            } else {
                botCaseLogic()
            }
        }

        bot_sq.setOnClickListener {
            if (selfStart && clockState == null) {
                botCaseLogic()
            } else {
                topCaseLogic()
            }
        }
    }

    /**
     * Switch cases on what to do on top / bot click,
     *  - startTimer top/bot
     *  - resetTimers on game end
     */
    private fun botCaseLogic() {
        when (clockState) {
            null -> startTimerBot(timeInSecondsBot)
            ClockStates.CLOCK_START -> startTimerBot(timeInSecondsBot) //add increments hier nog
            ClockStates.CLOCK_END -> resetTimers()
        }
    }

    private fun topCaseLogic() {
        when (clockState) {
            null -> startTimerTop(timeInSecondsTop)
            ClockStates.CLOCK_START -> startTimerTop(timeInSecondsTop) //add increments hier nog
            ClockStates.CLOCK_END -> resetTimers()
        }
    }

    /**
     * Set Theme settings on resuming MainActivity
     */
    override fun onResume() {
        super.onResume()
        setColorClocksToNeutral()
        dashBoard.setBackgroundColor(getColor(R.color.colorDashboard))

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        selfStart = sharedPreferences.getBoolean("SELF_START", false)

        when (sharedPreferences.getString("TURN_SOUND", null)) {
            "Select a sound..." -> mediaPlayer?.reset()
            "Clear throat" -> mediaPlayer = MediaPlayer.create(this, R.raw.clear_throat)
            "Mechanical click" -> mediaPlayer = MediaPlayer.create(this, R.raw.mechanical_switch)
        }
    }

    /**
     * Show dialog to restart game
     */
    private fun restartGame() {
        if (clockState == ClockStates.CLOCK_START || gamePaused) {
            gamePaused = false
            val dialog = AlertDialog.Builder(this)
                .setMessage("Restart?")
                .setPositiveButton(getString(R.string.yesStr)) { _, _ -> // dialog, whichButton are never used
                    resetTimers()
                    setColorClocksToNeutral()
                }
                .setNegativeButton(getString(R.string.noStr)) { _, _ -> // dialog, whichButton are never used
                    // Closes dialog
                }
                .create()
            dialog.setOnShowListener {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(getColor(R.color.colorActive))
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(getColor(R.color.colorActive))
            }
            dialog.show()
        }
    }

    /**
     * Pause game and set up for game to start with whichever player is preferred to start again
     */
    private fun pauseState() {
        pauseTimerTop()
        pauseTimerBot()
        setColorClocksToNeutral()
        gamePaused = true
    }

    /**
     * Set timers to new specified values
     */
    private fun resetTimers() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        pauseTimerTop()
        pauseTimerBot()
        timeInSecondsBot = sharedPreferences.getInt("BOT_TIME",900)
        timeInSecondsTop = sharedPreferences.getInt("TOP_TIME",900)
        updateTextUIBot()
        updateTextUITop()
    }

    /**
     * Pause timer and light up opponents screen
     */
    private fun pauseTimerBot() {
        if (this::countDownTimerBot.isInitialized) {
            countDownTimerBot.cancel()
            setColorsTopActive()
        }
    }

    private fun pauseTimerTop() {
        if (this::countDownTimerTop.isInitialized) {
            countDownTimerTop.cancel()
            setColorsBotActive()
        }
    }

    /**
     * Started opponent clock and run onStartTimer
     */
    private fun startTimerBot(seconds: Int) {
        onStartTimer()

        countDownTimerBot = object : CountDownTimer(seconds * 1000L, 1000) {
            override fun onFinish() {
                Log.d("T", "Bottom timer has ended!")
                clockState = ClockStates.CLOCK_END
            }

            override fun onTick(p0: Long) {
                timeInSecondsBot = (p0 / 1000L).toInt()
                updateTextUIBot()
            }
        }
        countDownTimerBot.start()

        setColorsBotActive()

        top_sq.isClickable = false
        bot_sq.isClickable = true
        pauseTimerTop()
    }

    /**
     * Started opponent clock and run onStartTimer
     */
    private fun startTimerTop(seconds: Int) {
        onStartTimer()

        countDownTimerTop = object : CountDownTimer(seconds * 1000L, 1000) {
            override fun onFinish() {
                Log.d("T", "Top timer has ended!")
                clockState = ClockStates.CLOCK_END
            }

            override fun onTick(p0: Long) {
                timeInSecondsTop = (p0 / 1000L).toInt()
                updateTextUITop()
            }
        }
        countDownTimerTop.start()

        setColorsTopActive()

        top_sq.isClickable = true
        bot_sq.isClickable = false
        pauseTimerBot()
    }

    /**
     * Code to run at Start of Timers
     */
    private fun onStartTimer() {
        gamePaused = false
        clockState = ClockStates.CLOCK_START
        mediaPlayer?.start()
        pause_button.visibility = View.VISIBLE
    }

    /**
     * Open Settings menu and pause MainActivity
     */
    private fun openSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        this.pauseState()
    }

    /**
     * Update timers
     */
    private fun updateTextUIBot() {
        bot_clock.text = Clock(timeInSecondsBot).updateText()
    }

    private fun updateTextUITop() {
        top_clock.text = Clock(timeInSecondsTop).updateText()
    }

    /**
     * Set colors to indicate next play
     */
    private fun setColorsTopActive() {
        top_sq.setBackgroundColor(getColor(R.color.colorActive))
        bot_sq.setBackgroundColor(getColor(R.color.colorInactive))
        top_clock.setTextColor(getColor(R.color.colorActiveText))
        bot_clock.setTextColor(getColor(R.color.colorInactiveText))
    }

    private fun setColorsBotActive() {
        bot_sq.setBackgroundColor(getColor(R.color.colorActive))
        top_sq.setBackgroundColor(getColor(R.color.colorInactive))
        bot_clock.setTextColor(getColor(R.color.colorActiveText))
        top_clock.setTextColor(getColor(R.color.colorInactiveText))
    }

    private fun setColorClocksToNeutral() {
        top_sq.setBackgroundColor(getColor(R.color.colorInactive))
        bot_sq.setBackgroundColor(getColor(R.color.colorInactive))
        top_clock.setTextColor(getColor(R.color.colorInactiveText))
        bot_clock.setTextColor(getColor(R.color.colorInactiveText))
        top_sq.isClickable = true
        bot_sq.isClickable = true
        clockState = null
        pause_button.visibility = View.GONE
    }

    /**
     * Load sharedPreferences
     */
    private fun loadData() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        isNightModeOn = sharedPreferences.getBoolean("BOOLEAN_KEY", false)
        selfStart = sharedPreferences.getBoolean("SELF_START", false)

        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    /**
     * Show dialog to close when 'Android back button' pressed
     */
    override fun onBackPressed() {
        val dialog = AlertDialog.Builder(this)
            .setMessage("Close?")
            .setPositiveButton(android.R.string.ok) { _, _ -> // dialog, whichButton are never used
                super.onBackPressed()
            }
            .setNegativeButton(android.R.string.cancel) { _, _ -> // dialog, whichButton are never used
                // Closes dialog
            }
            .create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(getColor(R.color.colorActive))
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(getColor(R.color.colorActive))
        }
        dialog.show()
    }
}