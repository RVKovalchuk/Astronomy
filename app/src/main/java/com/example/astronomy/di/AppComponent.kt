package com.example.astronomy.di

import android.content.Context
import com.example.astronomy.data.ApodDB
import com.example.astronomy.di.modules.DataModule
import com.example.astronomy.retrofit.AstronomyPictureOfTheDayService
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
abstract class AppComponent {
    abstract fun getWebService(): AstronomyPictureOfTheDayService
    abstract fun getDB(): ApodDB

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}