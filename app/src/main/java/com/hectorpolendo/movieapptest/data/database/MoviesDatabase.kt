package com.hectorpolendo.movieapptest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hectorpolendo.movieapptest.data.database.entities.*

@Database(entities = [MostPopularEntity::class, GenreEntity::class, TvShowEntity::class, ImageEntity::class, FavoritesEntity::class], version = 1, exportSchema = false)
@TypeConverters(GenresTypeConverter::class)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun movieDao(): MoviesDao
}