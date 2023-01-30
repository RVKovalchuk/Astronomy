package com.example.astronomy.di.modules

import android.content.Context
import androidx.room.Room
import com.example.astronomy.BuildConfig
import com.example.astronomy.data.ApodDAO
import com.example.astronomy.data.ApodDB
import com.example.astronomy.data.DataConstants.NAME_OF_DB
import com.example.astronomy.retrofit.AstronomyPictureOfTheDayService
import com.example.astronomy.retrofit.RetrofitAstronomyPictureOfTheDay
import com.example.astronomy.retrofit.RetrofitConstant.RETROFIT_URI
import com.example.astronomy.retrofit.RetrofitConstant.THIRTY_MAGIC_NUMBER
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
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .callTimeout(THIRTY_MAGIC_NUMBER, TimeUnit.SECONDS)
        .readTimeout(THIRTY_MAGIC_NUMBER, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BASIC
            }
        })
        .build()

    @Singleton
    @Provides
    fun provideDB(context: Context): ApodDB = Room.databaseBuilder(
        context, ApodDB::class.java, NAME_OF_DB
    ).build()

    @Singleton
    @Provides
    fun provideApodDAO(db: ApodDB): ApodDAO = db.apodDao()
}