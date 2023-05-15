package com.example.astronomy.view.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.astronomy.App
import com.example.astronomy.data.ApodFromView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailsViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = (app as App).repo
    private val mutableOpenedItem = MutableStateFlow<ApodFromView?>(null)
    val openedItem = mutableOpenedItem.asStateFlow()

    fun changeLike(apodFromView: ApodFromView) {
        repository.changeFavoriteState(apodFromView)
    }

    fun setItem(apodFromView: ApodFromView) {
        mutableOpenedItem.value = apodFromView
    }
}