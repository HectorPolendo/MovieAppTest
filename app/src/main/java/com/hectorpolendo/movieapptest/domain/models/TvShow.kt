package com.hectorpolendo.movieapptest.domain.models

import com.hectorpolendo.movieapptest.data.database.entities.TvShowEntity
import com.hectorpolendo.movieapptest.data.pojos.TvShowResponse

data class TvShow (
    val id: Int?,
    val backdrop_path: String?,
    val first_air_date: String?,
    val genre_ids: List<String>?,
    val name: String?,
    val origin_country: List<String>?,
    val original_language: String?,
    val original_name: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val vote_average: Double?,
    val vote_count: Int?
    )

    fun TvShowResponse.toDomain() = TvShow(
        id,
        backdrop_path,
        first_air_date,
        genre_ids,
        name,
        origin_country,
        original_language,
        original_name,
        overview,
        popularity,
        poster_path,
        vote_average,
        vote_count)

    fun TvShowEntity.toDomain() = TvShow(
        id,
        backdrop_path,
        first_air_date,
        genre_ids,
        name,
        origin_country,
        original_language,
        original_name,
        overview,
        popularity,
        poster_path,
        vote_average,
        vote_count)