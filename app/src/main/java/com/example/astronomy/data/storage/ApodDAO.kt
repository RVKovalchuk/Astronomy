package com.example.astronomy.data.storage

import androidx.room.*
import com.example.astronomy.data.retrofit.Apod
import com.example.astronomy.data.retrofit.FavoriteApod
import com.example.astronomy.data.storage.DataConstants.QUERY_DELETE_ALL
import com.example.astronomy.data.storage.DataConstants.QUERY_FAVORITE_ALL
import com.example.astronomy.data.storage.DataConstants.QUERY_GET_ALL
import kotlinx.coroutines.flow.Flow

@Dao
interface ApodDAO {
    @Query(QUERY_GET_ALL)
    fun getAll(): List<Apod>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Apod>)

    @Query(QUERY_DELETE_ALL)
    fun deleteAll()

    @Delete
    fun delete(apod: Apod)
}

@Dao
interface FavoriteApodDAO {
    @Query(QUERY_FAVORITE_ALL)
    fun getAll(): Flow<List<FavoriteApod>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(apod: FavoriteApod)

    @Delete
    fun delete(apod: FavoriteApod)

    @Query(QUERY_FAVORITE_ALL)
    fun getAllAsList(): List<FavoriteApod>
}