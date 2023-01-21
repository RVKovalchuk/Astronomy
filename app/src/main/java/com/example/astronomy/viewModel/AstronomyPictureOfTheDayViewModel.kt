package com.example.spaceinfo2.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astronomy.App
import com.example.astronomy.retrofit.AstronomyPictureOfTheDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AstronomyPictureOfTheDayViewModel : ViewModel() {
    val retrofit = App.app.dagger.getWebService()

    val apod = MutableLiveData<AstronomyPictureOfTheDay>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val astronomyPictureOfTheDayToday =
                retrofit.getAstronomyPictureOfTheDayToday()
            if (astronomyPictureOfTheDayToday != null) {
                apod.postValue(astronomyPictureOfTheDayToday!!)
                Log.d("!!!", astronomyPictureOfTheDayToday.title)

            } else {
                Log.d("!!!", "Problem with response")
            }
        }
    }
}