package com.example.astronomy.retrofit

import androidx.room.ColumnInfo
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class AstronomyPictureOfTheDay @JvmOverloads constructor(
    @Ignore @SerializedName("copyright") val copyright: String? = null,
    @PrimaryKey @SerializedName("date") val date: String,
    @ColumnInfo(name = "explanation") @SerializedName("explanation") val explanation: String,
    @Ignore @SerializedName("hdurl") val hdurl: String? = null,
    @Ignore @SerializedName("media_type") val media_type: String? = null,
    @Ignore @SerializedName("service_version") val service_version: String? = null,
    @ColumnInfo(name = "title") @SerializedName("title") val title: String,
    @ColumnInfo(name = "url") @SerializedName("url") val url: String
)