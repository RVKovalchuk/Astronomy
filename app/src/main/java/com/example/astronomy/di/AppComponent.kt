package com.example.astronomy.di

import android.content.Context
import com.example.astronomy.data.Repository
import com.example.astronomy.di.modules.DataModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
abstract class AppComponent {

    abstract val repo: Repository

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}