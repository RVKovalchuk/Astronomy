package com.example.astronomy.data.storage

object DataConstants {
    const val QUERY_GET_ALL = "SELECT * FROM search_table"
    const val QUERY_FAVORITE_ALL = "SELECT * FROM favorite_table"
    const val QUERY_DELETE_ALL = "DELETE FROM search_table"
    const val QUERY_DELETE_ALL_FROM_FAVORITES = "DELETE FROM favorite_table"
    const val NAME_OF_DB_ALL = "search_table"
    const val NAME_OF_DB_FAV = "favorite_table"
    const val COPYRIGHT = "copyright"
    const val EXPLANATION = "explanation"
    const val HDURL = "hdurl"
    const val MEDIA_TYPE = "media_type"
    const val SERVICE_VERSION = "service_version"
    const val TITLE = "title"
    const val URL = "url"
    const val DATE = "date"
    const val START_DATE = "2022-12-20"
    const val END_DATE = "2022-12-31"
    const val PATTERN_OF_DATE = "yyyy-MM-dd"

    const val VERSION_OF_DB = 1
    const val VERSION_OF_FAV_DB = 1
}