package com.example.astronomy

import android.app.Application
import com.example.astronomy.di.DaggerAppComponent

class App : Application() {
    val dagger by lazy {
        DaggerAppComponent.create()
    }

    override fun onCreate() {
        _app = this
        super.onCreate()
    }

    companion object {
        private var _app: App? = null
        val app: App
            get() {
                return _app!!
            }
    }
}