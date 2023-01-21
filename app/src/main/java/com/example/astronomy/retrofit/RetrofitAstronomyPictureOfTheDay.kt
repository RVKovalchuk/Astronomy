package com.example.astronomy.retrofit

import com.example.astronomy.retrofit.RetrofitConstant.RETROFIT_API_KEY
import com.example.astronomy.retrofit.RetrofitConstant.RETROFIT_END_DATE
import com.example.astronomy.retrofit.RetrofitConstant.RETROFIT_GET
import com.example.astronomy.retrofit.RetrofitConstant.RETROFIT_START_DATE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAstronomyPictureOfTheDay {
    @GET(RETROFIT_GET)
    suspend fun getAstronomyPictureOfTheDay(
        @Query(RETROFIT_API_KEY) key: String,
        @Query(RETROFIT_START_DATE) start_date: String,
        @Query(RETROFIT_END_DATE) end_date: String
    ): Response<List<AstronomyPictureOfTheDay>>

    @GET(RETROFIT_GET)
    suspend fun getAstronomyPictureOfTheDayToday(
        @Query(RETROFIT_API_KEY) key: String
    ): Response<AstronomyPictureOfTheDay>
}