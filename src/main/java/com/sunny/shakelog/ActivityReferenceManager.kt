package com.sunny.shakelog

import android.app.Activity
import android.os.Build
import java.lang.ref.WeakReference


class ActivityReferenceManager {
    private var wActivity: WeakReference<Activity>? = null
    fun setActivity(activity: Activity) {
        wActivity = WeakReference(activity)
    }

    val validatedActivity: Activity?
        get() {
            if (wActivity == null) {
                return null
            }
            val activity = wActivity!!.get()
            return if (!isActivityValid(activity)) {
                null
            } else activity
        }

    private fun isActivityValid(activity: Activity?): Boolean {
        if (activity == null) {
            return false
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            !activity.isFinishing && !activity.isDestroyed
        } else {
            !activity.isFinishing
        }
    }
}
