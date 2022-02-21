package com.hectorpolendo.movieapptest.domain.models

import com.hectorpolendo.movieapptest.data.database.entities.FavoritesEntity
import com.hectorpolendo.movieapptest.data.database.entities.MostPopularEntity
import com.hectorpolendo.movieapptest.data.pojos.MostPopularResponse

data class Movie(
    val id: Int?,
    val adult: Boolean?,
    val backdrop_path: String?,
    val genre_ids: List<String>?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
)

fun MostPopularResponse.toDomain() = Movie(
    id,
    adult,
    backdrop_path,
    genre_ids,
    original_language,
    original_title,
    overview,
    popularity,
    poster_path,
    release_date,
    title,
    video,
    vote_average,
    vote_count)

fun MostPopularEntity.toDomain() = Movie(
    id,
    adult,
    backdrop_path,
    genre_ids,
    original_language,
    original_title,
    overview,
    popularity,
    poster_path,
    release_date,
    title,
    video,
    vote_average,
    vote_count)

fun FavoritesEntity.toDomain() = Movie(
    id,
    adult,
    backdrop_path,
    genre_ids,
    original_language,
    original_title,
    overview,
    popularity,
    poster_path,
    release_date,
    title,
    video,
    vote_average,
    vote_count)