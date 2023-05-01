package com.example.astronomy.view.loading

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.astronomy.App
import com.example.astronomy.data.RepositoryState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoadingFragmentViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = (app as App).repo
    private var process: Job? = null

    private val mutableProgress = MutableStateFlow(0)
    val progress = mutableProgress.combine(repository.state) { p, s ->
        when {
            p < 99 -> p
            s == RepositoryState.Loaded -> p
            else -> 99
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    fun startProcess() {
        if (process != null) return
        process = viewModelScope.fakeProcess {
            mutableProgress.value = it
        }
    }

    fun stopProcess() {
        process?.cancel()
        process = null
        mutableProgress.value = 0
    }
}

fun CoroutineScope.fakeProcess(
    minTime: Int = 2000,
    maxTime: Int = 7000,
    onProgress: suspend (Int) -> Unit
): Job {
    return this.launch {
        val loadTime = (Math.random() * (maxTime - minTime) + minTime).toInt()
        val updateInterval = (loadTime / 100).toLong()

        for (i in 0..100) {
            onProgress(i)
            delay(updateInterval)
        }
    }
}