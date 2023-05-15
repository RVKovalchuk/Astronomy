package com.example.astronomy.data.storage

import com.example.astronomy.data.retrofit.Apod
import com.example.astronomy.data.retrofit.FavoriteApod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DbServiceImpl(apodDb: ApodDB) : DbService {
    private val apodService = apodDb.apodDao()
    private val favoriteApodService = apodDb.favoriteApodDao()

    override suspend fun addToFavorite(apod: Apod) {
        favoriteApodService.insert(FavoriteApod(apod.date))
    }

    override suspend fun removeFromFavorite(apod: Apod) {
        favoriteApodService.delete(FavoriteApod(apod.date))
    }

    override suspend fun getAll(): List<Apod> {
        return apodService.getAll()
    }

    override suspend fun removeAll() {
        val apods = getAll()
        val favoritesDate = favoriteApodService.getAllAsList().map { it.date }

        apods.forEach {
            if (!favoritesDate.contains(it.date)) {
                apodService.delete(it)
            }
        }
    }

    override fun getFavorites(): Flow<List<String>> {
        return favoriteApodService.getAll().map { it -> it.map { it.date } }
    }

    override suspend fun add(list: List<Apod>) {
        apodService.insert(list)
    }
}

interface DbService {
    suspend fun addToFavorite(apod: Apod)
    suspend fun removeFromFavorite(apod: Apod)
    suspend fun getAll(): List<Apod>
    suspend fun removeAll()
    fun getFavorites(): Flow<List<String>>
    suspend fun add(list: List<Apod>)
}