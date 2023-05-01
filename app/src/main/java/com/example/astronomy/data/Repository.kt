package com.example.astronomy.data

import android.content.Context
import android.util.Log
import com.example.astronomy.data.retrofit.Apod
import com.example.astronomy.data.retrofit.ApodService
import com.example.astronomy.data.retrofit.DateFormatter
import com.example.astronomy.data.storage.DbService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class RepositoryImpl(
    val dbService: DbService,
    val webService: ApodService
) : Repository {

    private val defaultStartDate by lazy {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        return@lazy Date(calendar.timeInMillis)
    }

    private val defaultEndDate by lazy {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        return@lazy Date(calendar.timeInMillis)
    }

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        CoroutineScope(Dispatchers.Main).launch {
            Log.i("VVV", throwable.message.toString())
            throw throwable
        }
    }
    private val coroutineScope = CoroutineScope(Dispatchers.IO + exceptionHandler)

    private val favoritesFromDb = dbService.getFavorites()

    private val mutableStartDate = MutableStateFlow(defaultStartDate)
    override val startDate: StateFlow<String?> = mutableStartDate.map { DateFormatter.format(it) }
        .stateIn(coroutineScope, SharingStarted.Eagerly, null)

    private val mutableEndDate = MutableStateFlow(defaultEndDate)
    override val endDate: StateFlow<String?> = mutableEndDate.map { DateFormatter.format(it) }
        .stateIn(coroutineScope, SharingStarted.Eagerly, null)

    private val mutableState = MutableStateFlow<RepositoryState>(RepositoryState.NotStarted)
    override val state: StateFlow<RepositoryState> = mutableState.asStateFlow()

    private val mutableDayApod = MutableStateFlow<ApodObservable?>(null)
    override val dayApod: StateFlow<ApodFromView?> =
        mutableDayApod.combine(favoritesFromDb, this::combine)
            .stateIn(coroutineScope, SharingStarted.Eagerly, null)

    private val all = MutableStateFlow<List<ApodObservable>?>(null)
    private val allCombined: StateFlow<List<ApodFromView>?> =
        all.combine(favoritesFromDb, this::combine)
            .stateIn(coroutineScope, SharingStarted.Eagerly, null)

    override val favoritesApods: StateFlow<List<ApodFromView>?> =
        allCombined.combine(favoritesFromDb, ::combineFavorites)
            .map { it -> it?.filter { it.favoriteState.value } }
            .stateIn(coroutineScope, SharingStarted.Eagerly, null)

    private val mutableSearched = MutableStateFlow<List<Apod>?>(null)
    override val searchedApods: StateFlow<List<Apod>?> = mutableSearched.asStateFlow()

    private fun combine(apod: ApodObservable?, favorites: List<String>?): ApodFromView? {
        return apod?.apply {
            apod.favoriteState.value = favorites?.contains(apod.apod.date) ?: false
        }
    }

    private fun combineFavorites(
        list: List<ApodFromView>?,
        list2: List<String>?
    ): List<ApodFromView>? {
        return list?.filter { list2?.any { str -> it.apod.date == str } ?: false }
    }

    private fun combine(
        apods: List<ApodObservable>?,
        favorites: List<String>?
    ): List<ApodFromView>? {
        return apods?.map { combine(it, favorites) ?: it }
    }

    override fun startLoad(context: Context) {

        if (state.value != RepositoryState.NotStarted && state.value != RepositoryState.Error) return

        mutableState.value = RepositoryState.Loading

        coroutineScope.launch {
            val dayApodLoad = coroutineScope.launch {
                val apod = webService.getTodayApod() ?: dbService.getAll().firstOrNull()
                mutableDayApod.value = apod?.toObs()
                if (apod != null) {
                    dbService.add(listOf(apod))
                }
            }
            val apodsLoad = coroutineScope.launch {
                var apods = webService.getApods(defaultStartDate, defaultEndDate)
                dayApodLoad.join()
                if (!apods.isNullOrEmpty()) {
                    dbService.add(apods)
                }

                apods = dbService.getAll()

                all.value = apods.map { it.toObs() }
                mutableSearched.value = apods
            }
            dayApodLoad.join()
            apodsLoad.join()

            val isSuccess = mutableDayApod.value != null && !all.value.isNullOrEmpty()
            mutableState.value = if (isSuccess) RepositoryState.Loaded else RepositoryState.Error
        }
    }

    override fun changeFavoriteState(apod: ApodFromView) {
        coroutineScope.launch {
            if (apod.favoriteState.value) {
                dbService.removeFromFavorite(apod.apod)
            } else {
                dbService.addToFavorite(apod.apod)
            }

            if (apod is ApodObservable) {
                apod.favoriteState.value = !apod.favoriteState.value
            }
        }
    }

    override fun search(from: Date, to: Date) {
        if (mutableStartDate.value == from && mutableEndDate == to) return
        mutableSearched.value = null

        coroutineScope.launch {
            val finded = webService.getApods(from, to)
            if (finded != null) {
                mutableSearched.value = finded
            }
        }
    }
}

interface Repository {

    val state: StateFlow<RepositoryState>
    val dayApod: StateFlow<ApodFromView?>
    val searchedApods: StateFlow<List<Apod>?>
    val favoritesApods: StateFlow<List<ApodFromView>?>
    val startDate: StateFlow<String?>
    val endDate: StateFlow<String?>

    fun startLoad(context: Context)
    fun changeFavoriteState(apod: ApodFromView)
    fun search(from: Date, to: Date)
}

sealed interface RepositoryState {
    object NotStarted : RepositoryState
    object Loading : RepositoryState
    object Loaded : RepositoryState
    object Error : RepositoryState
}

interface ApodFromView {
    val apod: Apod
    val favoriteState: StateFlow<Boolean>
}

class ApodObservable(
    override val apod: Apod,
    override val favoriteState: MutableStateFlow<Boolean>
) : ApodFromView

fun Apod.toObs(isFavorite: Boolean = false) = ApodObservable(this, MutableStateFlow(isFavorite))