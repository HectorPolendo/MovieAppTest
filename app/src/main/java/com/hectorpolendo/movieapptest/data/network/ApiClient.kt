package com.hectorpolendo.movieapptest.data.network

import com.hectorpolendo.movieapptest.data.pojos.GenreResultResponse
import com.hectorpolendo.movieapptest.data.pojos.MostPopularResultResponse
import com.hectorpolendo.movieapptest.data.pojos.TvShowResultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {
    @GET("movie/popular?")
    suspend fun getMostPopular(
        @Query("api_key") api: String,
        @Query("language") language: String,
        @Query("page") page: String): Response<MostPopularResultResponse>

    @GET("genre/movie/list?")
    suspend fun getGenres(
        @Query("api_key") api: String,
        @Query("language") language: String): Response<GenreResultResponse>

    @GET("tv/popular?")
    suspend fun getTvShows(
        @Query("api_key") api: String,
        @Query("language") language: String,
        @Query("page") page: String): Response<TvShowResultResponse>


}