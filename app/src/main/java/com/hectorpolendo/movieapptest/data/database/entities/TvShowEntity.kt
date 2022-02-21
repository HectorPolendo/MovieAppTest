package com.hectorpolendo.movieapptest.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hectorpolendo.movieapptest.domain.models.Movie
import com.hectorpolendo.movieapptest.domain.models.TvShow
import javax.annotation.Nullable

@Entity(tableName = "TvShows")
data class TvShowEntity (
    @PrimaryKey(autoGenerate = false)
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

fun TvShow.toDatabase() = TvShowEntity(
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
    vote_count
)