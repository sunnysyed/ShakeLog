package com.sunny.shakelog

import android.app.Activity
import android.app.Application
import android.os.Bundle


internal abstract class SimpleActivityLifecycleCallback : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        // This method intentionally left blank
    }

    override fun onActivityResumed(activity: Activity) {
        // This method intentionally left blank
    }

    override fun onActivityStarted(activity: Activity) {
        // This method intentionally left blank
    }

    override fun onActivityPaused(activity: Activity) {
        // This method intentionally left blank
    }

    override fun onActivityStopped(activity: Activity) {
        // This method intentionally left blank
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        // This method intentionally left blank
    }

    override fun onActivityDestroyed(activity: Activity) {
        // This method intentionally left blank
    }
}