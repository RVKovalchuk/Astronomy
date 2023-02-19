package com.example.astronomy.retrofit

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.astronomy.data.DataConstants.COPYRIGHT
import com.example.astronomy.data.DataConstants.DATE
import com.example.astronomy.data.DataConstants.EXPLANATION
import com.example.astronomy.data.DataConstants.HDURL
import com.example.astronomy.data.DataConstants.IS_FAVORITE
import com.example.astronomy.data.DataConstants.MEDIA_TYPE
import com.example.astronomy.data.DataConstants.NAME_OF_DB
import com.example.astronomy.data.DataConstants.SERVICE_VERSION
import com.example.astronomy.data.DataConstants.TITLE
import com.example.astronomy.data.DataConstants.URL
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = NAME_OF_DB)
@Parcelize
data class AstronomyPictureOfTheDay @JvmOverloads constructor(
    @Ignore @SerializedName(COPYRIGHT) val copyright: String? = null,
    @PrimaryKey @SerializedName(DATE) val date: String,
    @ColumnInfo(name = EXPLANATION) @SerializedName(EXPLANATION) val explanation: String,
    @Ignore @SerializedName(HDURL) val hdurl: String? = null,
    @Ignore @SerializedName(MEDIA_TYPE) val media_type: String? = null,
    @Ignore @SerializedName(SERVICE_VERSION) val service_version: String? = null,
    @ColumnInfo(name = TITLE) @SerializedName(TITLE) val title: String,
    @ColumnInfo(name = URL) @SerializedName(URL) val url: String,
    @ColumnInfo(name = IS_FAVORITE) var isFavorite: Boolean = false
) : Parcelable