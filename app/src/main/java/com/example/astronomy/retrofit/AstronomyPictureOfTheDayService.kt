package com.example.astronomy.retrofit

import com.example.astronomy.retrofit.RetrofitConstant.RETROFIT_KEY
import com.example.spaceinfo2.viewModel.DateFormatter
import java.util.*

class AstronomyPictureOfTheDayService(private val apod: RetrofitAstronomyPictureOfTheDay) {
    suspend fun getAstronomyPictureOfTheDay(
        startDate: Date,
        endDate: Date
    ): List<AstronomyPictureOfTheDay>? {
        return apod.getAstronomyPictureOfTheDay(
            key = RETROFIT_KEY,
            start_date = DateFormatter.format(startDate),
            end_date = DateFormatter.format(endDate)
        ).body()
    }

    suspend fun getAstronomyPictureOfTheDayToday(): AstronomyPictureOfTheDay? {
        return apod.getAstronomyPictureOfTheDayToday(
            key = RETROFIT_KEY
        ).body()
    }
}