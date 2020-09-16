package com.swvltask.decadeofmovies.shared

import android.app.Application
import com.swvltask.decadeofmovies.shared.di.homeModule
import com.swvltask.decadeofmovies.shared.di.networkModule
import com.swvltask.decadeofmovies.shared.di.reposModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules (
                listOf(reposModule, homeModule, networkModule)
            )
        }
    }
}