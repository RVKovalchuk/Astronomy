package com.example.astronomy.di.modules

import android.content.Context
import androidx.room.Room
import com.example.astronomy.data.Repository
import com.example.astronomy.data.RepositoryImpl
import com.example.astronomy.data.retrofit.ApodService
import com.example.astronomy.data.retrofit.RetrofitApodService
import com.example.astronomy.data.retrofit.RetrofitConstant
import com.example.astronomy.data.storage.ApodDB
import com.example.astronomy.data.storage.DbService
import com.example.astronomy.data.storage.DbServiceImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.LoggingEventListener
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideRepository(dbService: DbService, webService: ApodService): Repository {
        return RepositoryImpl(dbService, webService)
    }

    @Singleton
    @Provides
    fun provideDbService(apodDB: ApodDB): DbService {
        return DbServiceImpl(apodDB)
    }

    @Singleton
    @Provides
    fun provideApodDb(context: Context): ApodDB {
        return Room.databaseBuilder(context, ApodDB::class.java, "APOD_DB").build()
    }

    @Singleton
    @Provides
    fun provideApodService(retrofitService: RetrofitApodService): ApodService {
        return ApodService(retrofitService)
    }

    @Singleton
    @Provides
    fun provideRetrofitService(): RetrofitApodService {
        return Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .callTimeout(8000L, TimeUnit.MILLISECONDS)
                    .connectTimeout(8000L, TimeUnit.MILLISECONDS)
                    .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(RetrofitConstant.RETROFIT_URI)
            .build().create(RetrofitApodService::class.java)
    }
}