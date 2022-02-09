package ru.barinov.bettapplication.ui

import android.app.Application
import org.koin.android.ext.koin.*
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.barinov.bettapplication.core.appModule

class BettingStrategyApplication: Application() {

    override fun onCreate() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@BettingStrategyApplication)
            modules(listOf(appModule))
        }
        super.onCreate()
    }
}