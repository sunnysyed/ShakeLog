package com.sunny.shakelog.demo

import android.app.Application
import com.sunny.shakelog.ShakeLog

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ShakeLog.init(this)
    }
}