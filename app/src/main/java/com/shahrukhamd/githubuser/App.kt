/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser

import android.app.Application
import com.shahrukhamd.githubuser.utils.CrashReportingTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()

        // plant a custom Timber tree for logging
        Timber.plant(if (BuildConfig.DEBUG) {
            Timber.DebugTree()
        } else {
            CrashReportingTree()
        })
    }
}