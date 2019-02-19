package com.topgames.ccouto.topgames

import android.app.Application
import android.util.Log
import java.lang.IllegalStateException

/**
 * Created by ccouto on 19/02/2019.
 */
class TopGamesApplicationApplication : Application() {
    private val TAG = "TopGamesApplication"

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: TopGamesApplicationApplication? = null

        fun getInstance(): TopGamesApplicationApplication {
            if (appInstance == null) {
                throw IllegalStateException("Configure a classe de Application no AndroidManifest.xml")
            }
            return appInstance!!
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.d(TAG, "onTerminate()")
    }
}