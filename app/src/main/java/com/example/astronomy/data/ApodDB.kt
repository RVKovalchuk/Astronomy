package com.example.astronomy.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.astronomy.data.DataConstants.VERSION_OF_DB
import com.example.astronomy.retrofit.AstronomyPictureOfTheDay

@Database(
    entities = [AstronomyPictureOfTheDay::class],
    version = VERSION_OF_DB,
    exportSchema = false
)
abstract class ApodDB : RoomDatabase() {
    abstract fun apodDao(): ApodDAO
}