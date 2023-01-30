package com.example.astronomy.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.astronomy.App
import com.example.astronomy.data.DataConstants.END_DATE
import com.example.astronomy.data.DataConstants.PATTERN_OF_DATE
import com.example.astronomy.data.DataConstants.START_DATE
import com.example.astronomy.retrofit.AstronomyPictureOfTheDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AstronomyPictureOfTheDayRecyclerViewModel(app: Application) : AndroidViewModel(app) {
    val retrofit = (app as App).dagger.getWebService()
    private val db = (app as App).dagger.getDB()

    val apodList = MutableLiveData<List<AstronomyPictureOfTheDay>?>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val listAstronomyPictureOfTheDayToday =
                retrofit.getAstronomyPictureOfTheDay(
                    DateFormatter.parse(START_DATE) ?: Date(),
                    DateFormatter.parse(END_DATE) ?: Date()
                )
            if (listAstronomyPictureOfTheDayToday != null) {
                apodList.postValue(listAstronomyPictureOfTheDayToday)
                db.apodDao().deleteAllApodsFromDB()
                db.apodDao().insertAllApodsToDB(listAstronomyPictureOfTheDayToday)
            } else {
                apodList.postValue(db.apodDao().getAllApodsFromDB())
            }
        }
    }
}

object DateFormatter : SimpleDateFormat(PATTERN_OF_DATE, Locale.US)