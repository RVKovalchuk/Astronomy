package com.example.astronomy.di.modules

import com.example.astronomy.BuildConfig
import com.example.astronomy.retrofit.AstronomyPictureOfTheDayService
import com.example.astronomy.retrofit.RetrofitAstronomyPictureOfTheDay
import com.example.astronomy.retrofit.RetrofitConstant.RETROFIT_URI
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DataModule {
    @Singleton
    @Provides
    fun provideRetrofit(apod: RetrofitAstronomyPictureOfTheDay): AstronomyPictureOfTheDayService =
        AstronomyPictureOfTheDayService(apod = apod)

    @Singleton
    @Provides
    fun provideAstronomyPicturesOfTheDay(okHttpClient: OkHttpClient): RetrofitAstronomyPictureOfTheDay =
        Retrofit.Builder()
            .baseUrl(RETROFIT_URI)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RetrofitAstronomyPictureOfTheDay::class.java)

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient = OkHttpClient.Builder()
        .callTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BASIC
            }
        })
        .build()
}