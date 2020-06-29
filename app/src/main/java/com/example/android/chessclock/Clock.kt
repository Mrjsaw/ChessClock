package com.example.android.chessclock


class Clock(timeSeconds : Long) {
    val timeSeconds = timeSeconds

    fun updateText(): String {
        if (timeSeconds < 60) {
            if (timeSeconds < 10) {
                return "00:0$timeSeconds"
            }
            return "00:$timeSeconds"
        }
        else if (timeSeconds <= 3600) {
            val mins = timeSeconds/60
            val secs = timeSeconds - (mins * 60)
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
        //only support up to 1 hour Time Formats
        return "-1:-1"
    }
}