package com.example.android.chessclock


class Clock(timeSeconds : Long) {
    val timeSeconds = timeSeconds
    fun updateText(): String {
        if (timeSeconds < 60) {
            return "00:$timeSeconds"
        }
        else if (timeSeconds < 3600) {
            val mins = timeSeconds/60
            val secs = timeSeconds - (mins * 60)
            if (mins < 10) {
                return "0$mins:$secs"
            }
            return "$mins:$secs"
        }
        //only support up to 1 hour Time Formats
        return "-1:-1"
    }
}