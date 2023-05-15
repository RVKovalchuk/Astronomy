package com.example.astronomy.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.astronomy.data.storage.DataConstants.VERSION_OF_DB
import com.example.astronomy.data.retrofit.Apod
import com.example.astronomy.data.retrofit.FavoriteApod

@Database(
    entities = [Apod::class, FavoriteApod::class],
    version = VERSION_OF_DB,
    exportSchema = false
)

abstract class ApodDB : RoomDatabase() {
    abstract fun apodDao(): ApodDAO
    abstract fun favoriteApodDao(): FavoriteApodDAO
}