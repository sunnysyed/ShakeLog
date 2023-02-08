package com.sunny.shakelog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.hardware.SensorManager
import android.os.Parcel
import android.os.Parcelable
import java.util.*

const val SHAKE_LOG = "share_log"

class ShakeLog private constructor(private val application: Application) : ShakeDetector.Listener {
    private val logger: Logger = Logger(BuildConfig.DEBUG)
    private var log: ArrayList<LogItem> = arrayListOf()
    private val activityReferenceManager = ActivityReferenceManager()
    private var logOpened = false
    private val simpleActivityLifecycleCallback: SimpleActivityLifecycleCallback =
        object : SimpleActivityLifecycleCallback() {
            override fun onActivityResumed(activity: Activity) {
                activityReferenceManager.setActivity(activity)
                logOpened = activity is LogActivity
            }
        }

    init {
        application.registerActivityLifecycleCallbacks(simpleActivityLifecycleCallback);
        val sensorManager =
            application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val shakeDetector = ShakeDetector(this)
        val didStart = shakeDetector.start(sensorManager)
        if (didStart) {
            logger.d("Shake detection successfully started!")
        } else {
            logger.e("Error starting shake detection: hardware does not support detection.")
        }
    }

    override fun hearShake() {
        logger.d("Shake detected!")
        if (activityReferenceManager.validatedActivity !is LogActivity) {
            openLog()
        }
    }

    private fun openLog() {
        activityReferenceManager.validatedActivity?.startActivity(
            Intent(
                application.applicationContext,
                LogActivity::class.java
            ).apply {
                putParcelableArrayListExtra(SHAKE_LOG, log)
            })
    }

    private fun logEvent(key: String, value: String) {
        log.add(LogItem(key, value))
    }

    private fun clearLog() {
        log.clear()
    }

    companion object {
        @SuppressLint("StaticFieldLeak") // we're holding the application context.
        private var sharedInstance: ShakeLog? = null

        /**
         * @param application the embedding application
         * @return the singleton `ShakeLog` instance
         */
        fun init(application: Application): ShakeLog {
            synchronized(ShakeLog::class.java) {
                if (sharedInstance == null) {
                    sharedInstance = ShakeLog(application)
                }
            }
            return (sharedInstance)!!
        }

        fun logEvent(key: String, value: String) {
            if (sharedInstance == null) {
                throw java.lang.IllegalStateException("ShakeLog has not been initialized call init before trying to log an event")
            } else {
                sharedInstance?.logEvent(key, value)
            }
        }

        fun clearLog() {
            if (sharedInstance == null) {
                throw java.lang.IllegalStateException("ShakeLog has not been initialized call init before trying to clear the log")
            } else {
                sharedInstance?.clearLog()
            }
        }

        fun openLog() {
            if (sharedInstance == null) {
                throw java.lang.IllegalStateException("ShakeLog has not been initialized call init before trying to clear the log")
            } else {
                sharedInstance?.openLog()
            }
        }
    }
}

internal data class LogItem(
    val key: String,
    val value: String,
    val unixTime: Long = Date().time
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeString(value)
        parcel.writeLong(unixTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LogItem> {
        override fun createFromParcel(parcel: Parcel): LogItem {
            return LogItem(parcel)
        }

        override fun newArray(size: Int): Array<LogItem?> {
            return arrayOfNulls(size)
        }
    }
}
