package com.example.astronomy.data.retrofit

import android.util.Log
import com.example.astronomy.data.storage.DataConstants
import com.example.astronomy.data.retrofit.RetrofitConstant.RETROFIT_KEY
import java.text.SimpleDateFormat
import java.util.*

class ApodService(private val apod: RetrofitApodService) {

    suspend fun getApods(
        startDate: Date,
        endDate: Date
    ): List<Apod>? {
        return try {
            Log.i("VVV", "Service Start")
            val result = apod.getAstronomyPictureOfTheDay(
                key = RETROFIT_KEY,
                start_date = DateFormatter.format(startDate),
                end_date = DateFormatter.format(endDate)
            ).body()
            Log.i("VVV","Service Loaded")
            result
        } catch (e: Exception) {
            Log.i("VVV", "Service Error ${e.message}")
            null
        }
    }

    suspend fun getTodayApod(): Apod? {
        return try {
            apod.getAstronomyPictureOfTheDayToday(
                key = RETROFIT_KEY
            ).body()
        } catch (e: Exception) {
            null
        }
    }
}

object DateFormatter : SimpleDateFormat(DataConstants.PATTERN_OF_DATE, Locale.US)