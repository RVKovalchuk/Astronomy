package com.example.astronomy

import android.app.Application
import com.example.astronomy.di.DaggerAppComponent

class App : Application() {

    private val appComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    val repo by lazy {
        appComponent.repo
    }

    override fun onCreate() {
        super.onCreate()
        repo.startLoad(this)
    }
}
