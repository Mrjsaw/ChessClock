package com.example.android.chessclock


class Clock(private val timeSeconds: Int) {

    fun updateText(): String {
        if (timeSeconds >= 60) {
            val mins = timeSeconds/60
            val secs = timeSeconds%60
            var minutes = mins.toString()
            var seconds = secs.toString()
            if (mins < 10) {
                minutes = "0$minutes"
            }
            if (secs < 10) {
                seconds = "0$seconds"
            }
            return "$minutes:$seconds"
        }
        else{
            if (timeSeconds < 10) {
                return "00:0$timeSeconds"
            }
            return "00:$timeSeconds"
        }
    }
}