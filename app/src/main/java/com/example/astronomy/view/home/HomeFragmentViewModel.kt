package com.example.astronomy.view.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.astronomy.App
import com.example.astronomy.data.ApodFromView

class HomeFragmentViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = (app as App).repo

    val apod = repository.dayApod

    fun changeFavoriteState(apod: ApodFromView) {
        repository.changeFavoriteState(apod)
    }
}