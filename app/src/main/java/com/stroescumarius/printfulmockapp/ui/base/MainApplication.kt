package com.stroescumarius.printfulmockapp.ui.base

import android.app.Application
import com.stroescumarius.printfulmockapp.data.koin.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

class MainApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(appModules)
        }
    }
}