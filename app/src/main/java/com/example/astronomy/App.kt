package com.example.astronomy

import android.app.Application
import com.example.astronomy.di.DaggerAppComponent

class App : Application() {
    val dagger by lazy {
        DaggerAppComponent.factory().create(this)
    }
}