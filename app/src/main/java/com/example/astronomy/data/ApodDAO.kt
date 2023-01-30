package com.example.astronomy.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.astronomy.data.DataConstants.QUERY_DELETE_ALL
import com.example.astronomy.data.DataConstants.QUERY_GET_ALL
import com.example.astronomy.retrofit.AstronomyPictureOfTheDay

@Dao
interface ApodDAO {
    @Query(QUERY_GET_ALL)
    suspend fun getAllApodsFromDB(): List<AstronomyPictureOfTheDay>

    @Insert
    suspend fun insertAllApodsToDB(list: List<AstronomyPictureOfTheDay>)

    @Query(QUERY_DELETE_ALL)
    suspend fun deleteAllApodsFromDB()
}