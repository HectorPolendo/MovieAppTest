package com.hectorpolendo.movieapptest.domain.models

import com.hectorpolendo.movieapptest.data.database.entities.GenreEntity
import com.hectorpolendo.movieapptest.data.pojos.GenreResponse

data class Genre(
    val id: Int,
    val name: String
)

fun GenreResponse.toDomain() = Genre(
    id,
    name)

fun GenreEntity.toDomain() = Genre(
    id,
    name)