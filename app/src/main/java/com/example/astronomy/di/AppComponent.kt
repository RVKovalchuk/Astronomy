package com.example.astronomy.di

import com.example.astronomy.retrofit.AstronomyPictureOfTheDayService
import com.example.astronomy.di.modules.DataModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
abstract class AppComponent {
    abstract fun getWebService() : AstronomyPictureOfTheDayService
}