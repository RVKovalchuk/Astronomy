package com.example.astronomy.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.astronomy.App
import com.example.astronomy.retrofit.AstronomyPictureOfTheDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
class FavoriteAstronomyPictureOfTheDaysViewModel (app: Application) : AndroidViewModel(app) {
    val retrofit = (app as App).dagger.getWebService()
    val apodFavorite = MutableLiveData<AstronomyPictureOfTheDay?>()

    init {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }
}*/