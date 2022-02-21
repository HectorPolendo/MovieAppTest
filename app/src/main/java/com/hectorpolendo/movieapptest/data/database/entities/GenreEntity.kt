package com.hectorpolendo.movieapptest.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hectorpolendo.movieapptest.domain.models.Genre

@Entity(tableName = "Genres")
data class GenreEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String
)

fun Genre.toDatabase() = GenreEntity(
    id,
    name
)