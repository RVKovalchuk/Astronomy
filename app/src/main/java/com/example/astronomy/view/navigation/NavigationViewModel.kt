package com.example.astronomy.view.navigation

import androidx.annotation.IdRes
import androidx.lifecycle.ViewModel
import com.example.astronomy.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationViewModel : ViewModel() {

    private val mutableNavState = MutableStateFlow<NavState>(NavState.Home)
    val navState = mutableNavState.asStateFlow()

    fun navigate(navState: NavState) {
        mutableNavState.value = navState
    }

    fun navigate(@IdRes destination: Int) {
        navigate(
            when (destination) {
                R.id.search -> NavState.Search
                R.id.favorites -> NavState.Favorites
                else -> NavState.Home
            }
        )
    }
}

sealed class NavState {
    object Home : NavState()
    object Search : NavState()
    object Favorites : NavState()
    object Settings : NavState()
}