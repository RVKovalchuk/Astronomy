package com.example.astronomy.view.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.astronomy.App

class FavoritesViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = (app as App).repo

    val favorites = repository.favoritesApods

    var recyclerScrollState = 0
}