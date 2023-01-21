package com.example.spaceinfo2.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astronomy.App
import com.example.astronomy.retrofit.AstronomyPictureOfTheDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AstronomyPictureOfTheDayRecyclerViewModel : ViewModel() {
    val retrofit = App.app.dagger.getWebService()

    val apodList = MutableLiveData<List<AstronomyPictureOfTheDay>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val listAstronomyPictureOfTheDayToday =
                retrofit.getAstronomyPictureOfTheDay(DateFormatter.parse("2022-12-01")?: Date(), DateFormatter.parse("2022-12-31")?: Date())
            if (listAstronomyPictureOfTheDayToday != null) {
                apodList.postValue(listAstronomyPictureOfTheDayToday!!)
                Log.d("!!!", listAstronomyPictureOfTheDayToday.toString())

            } else {
                Log.d("!!!", "Problem with response")
            }
        }
    }
}



object DateFormatter: SimpleDateFormat("yyyy-MM-dd", Locale.US)