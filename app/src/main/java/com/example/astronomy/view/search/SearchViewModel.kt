package com.example.astronomy.view.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.astronomy.App

class SearchViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = (app as App).repo

    var rvState: Int = 0

    val searched = repo.searchedApods

    val startDate = repo.startDate
    val endDate = repo.endDate
}