package com.hectorpolendo.movieapptest.data.network

import com.hectorpolendo.movieapptest.data.pojos.GenreResponse
import com.hectorpolendo.movieapptest.data.pojos.MostPopularResponse
import com.hectorpolendo.movieapptest.data.pojos.TvShowResponse
import com.hectorpolendo.movieapptest.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ServiceController @Inject constructor(private val api: ApiClient) {

    suspend fun getMostPopular(): List<MostPopularResponse> {
        return withContext(Dispatchers.IO){
            val response = api.getMostPopular(Constants.API_KEY, "en-US", "1")
            response.body()?.results ?: emptyList()
        }
    }

    suspend fun getGenres(): List<GenreResponse> {
        return withContext(Dispatchers.IO){
            val response = api.getGenres(Constants.API_KEY, "en-US")
            response.body()?.genres ?: emptyList()
        }
    }

    suspend fun getTvShows(): List<TvShowResponse> {
        return withContext(Dispatchers.IO){
            val response = api.getTvShows(Constants.API_KEY, "en-US", "1")
            response.body()?.results ?: emptyList()
        }
    }

}