package com.hectorpolendo.movieapptest.util

import android.Manifest
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts

object Constants {
    const val API_KEY: String = "239515cdb80d47b2d1289548ca8b8e10"
    const val PATH_IMGS: String = "https://image.tmdb.org/t/p/w500"
    const val MOVIE_ID: String = "MOVIE_ID"
    const val MOVIE_NAME: String = "MOVIE_NAME"
    const val MOVIE_IMG: String = "MOVIE_IMG"
    const val SELECT_PICTURE = 200
    var FROM: String? = null

    var PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

}