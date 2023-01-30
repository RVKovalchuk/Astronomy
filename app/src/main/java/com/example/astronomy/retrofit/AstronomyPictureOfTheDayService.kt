package com.example.astronomy.retrofit

import com.example.astronomy.retrofit.RetrofitConstant.RETROFIT_KEY
import com.example.astronomy.viewModel.DateFormatter
import java.util.*

class AstronomyPictureOfTheDayService(private val apod: RetrofitAstronomyPictureOfTheDay) {
    suspend fun getAstronomyPictureOfTheDay(
        startDate: Date,
        endDate: Date
    ): List<AstronomyPictureOfTheDay>? {
        return try {
            apod.getAstronomyPictureOfTheDay(
                key = RETROFIT_KEY,
                start_date = DateFormatter.format(startDate),
                end_date = DateFormatter.format(endDate)
            ).body()
        } catch (e: Exception) {
            return null
        }

    }

    suspend fun getAstronomyPictureOfTheDayToday(): AstronomyPictureOfTheDay? {
        return try {
            apod.getAstronomyPictureOfTheDayToday(
                key = RETROFIT_KEY
            ).body()
        } catch (e: Exception) {
            null
        }
    }
}