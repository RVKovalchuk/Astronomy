package com.example.astronomy.data.retrofit

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.astronomy.data.storage.DataConstants.COPYRIGHT
import com.example.astronomy.data.storage.DataConstants.DATE
import com.example.astronomy.data.storage.DataConstants.EXPLANATION
import com.example.astronomy.data.storage.DataConstants.HDURL
import com.example.astronomy.data.storage.DataConstants.MEDIA_TYPE
import com.example.astronomy.data.storage.DataConstants.NAME_OF_DB_ALL
import com.example.astronomy.data.storage.DataConstants.NAME_OF_DB_FAV
import com.example.astronomy.data.storage.DataConstants.SERVICE_VERSION
import com.example.astronomy.data.storage.DataConstants.TITLE
import com.example.astronomy.data.storage.DataConstants.URL
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = NAME_OF_DB_ALL)
@Parcelize
class Apod @JvmOverloads constructor(
    @Ignore @SerializedName(COPYRIGHT) val copyright: String? = null,
    @PrimaryKey @SerializedName(DATE) val date: String,
    @ColumnInfo(name = EXPLANATION) @SerializedName(EXPLANATION) val explanation: String,
    @Ignore @SerializedName(HDURL) val hdurl: String? = null,
    @Ignore @SerializedName(MEDIA_TYPE) val media_type: String? = null,
    @Ignore @SerializedName(SERVICE_VERSION) val service_version: String? = null,
    @ColumnInfo(name = TITLE) @SerializedName(TITLE) val title: String,
    @ColumnInfo(name = URL) @SerializedName(URL) val url: String
) : Parcelable

@Entity(tableName = NAME_OF_DB_FAV)
@Parcelize
class FavoriteApod(
    @PrimaryKey val date: String
) : Parcelable
