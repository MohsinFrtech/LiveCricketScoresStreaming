package com.traumsportzone.live.cricket.tv.scores.utils

import android.util.Log

class Logger {

    private val isValueEnabledOrDisabled = true

    fun printLog(tag: String, message: String) {
        if (isValueEnabledOrDisabled) {
            Log.d(tag, "Value $message")
        }
    }
}