package com.topgames.ccouto.topgames

import android.app.Application
import android.util.Log
import java.lang.IllegalStateException

/**
 * Created by ccouto on 14/11/2017.
 */
class TopGamesApplicationApplication : Application() {
    private val TAG = "TopGamesApplication"

    val flagSendLog = true

    // Chamado quando o Android criar o processo da aplicação
    override fun onCreate() {
        super.onCreate()
        // Salva a instância para termos acesso como Singleton
        appInstance = this
    }

    companion object {
        // Singleton da classe Application
        private var appInstance: TopGamesApplicationApplication? = null

        fun getInstance(): TopGamesApplicationApplication {
            if (appInstance == null) {
                throw IllegalStateException("Configure a classe de Application no AndroidManifest.xml")
            }
            return appInstance!!
        }
    }

    // Chamado quando o Android finalizar o processo da aplicação
    override fun onTerminate() {
        super.onTerminate()
        Log.d(TAG, "onTerminate()")
    }
}