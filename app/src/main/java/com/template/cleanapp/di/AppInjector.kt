package com.template.cleanapp.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.template.cleanapp.CleanApplication

/**
 * Helper class to automatically inject fragments if they implement [Injectable].
 * Created by Lajesh Dineshkumar on 10/30/2019.
 * Company: Nagarro Middle East
 * Email: lajesh.dineshkumar@nagarro.com
 */
object AppInjector {

    private var resumed = 0
    private var paused = 0

    fun init(mCleanApplication: CleanApplication) {
        mCleanApplication.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                // Update Locale context
            }

            override fun onActivityStarted(activity: Activity) {
                // Nothing goes here
            }

            override fun onActivityResumed(activity: Activity) {
                ++resumed
                CleanApplication.setAppVisible(true)
            }

            override fun onActivityPaused(activity: Activity) {
                ++paused
            }

            override fun onActivityStopped(activity: Activity) {
                CleanApplication.setAppVisible(resumed > paused)
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
                // Nothing goes here
            }

            override fun onActivityDestroyed(activity: Activity) {
                // Nothing goes here
            }
        })
    }
}
