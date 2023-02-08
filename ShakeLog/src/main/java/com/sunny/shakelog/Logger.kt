package com.sunny.shakelog

import android.util.Log


class Logger(private val loggingEnabled: Boolean) {
    fun d(message: CharSequence) {
        if (loggingEnabled) {
            Log.d(TAG, message.toString())
        }
    }

    fun e(message: CharSequence) {
        if (loggingEnabled) {
            Log.e(TAG, message.toString())
        }
    }

    fun printStackTrace(throwable: Throwable) {
        if (loggingEnabled) {
            Log.e(TAG, "Logging caught exception", throwable)
        }
    }

    companion object {
        private const val TAG = "ShakeLog"
    }
}