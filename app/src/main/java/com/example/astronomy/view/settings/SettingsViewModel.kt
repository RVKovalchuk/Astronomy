package com.example.astronomy.view.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.astronomy.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(app: Application): AndroidViewModel(app) {

    private val repo = (app as App).repo

    val nightMode = repo.nightMod

    fun clearCash(onClear: () -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.clearCash()
            onClear()
        }
    }

    fun changeRepoNigthState(isNigth: Boolean) {
        repo.changeNigthState(isNigth)
    }
}