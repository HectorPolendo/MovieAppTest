package com.hectorpolendo.movieapptest.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hectorpolendo.movieapptest.data.database.entities.*

@Dao
interface MoviesDao {

    /**
     MOST POPULAR
     **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMostPopular(mostPopular: List<MostPopularEntity>)

    @Query("SELECT * FROM MostPopular")
    suspend fun readMostPopular(): List<MostPopularEntity>

    @Query("SELECT * FROM MostPopular WHERE id = :id")
    suspend fun readMostPopularById(id: Int): MostPopularEntity

    @Query("DELETE FROM MostPopular")
    suspend fun clearMostPopular()

    /**
    GENRES
     **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenreEntity>)

    @Query("SELECT * FROM Genres")
    suspend fun readGenres(): List<GenreEntity>

    @Query("DELETE FROM Genres")
    suspend fun clearGenres()

    /**
     TV SHOWS
     **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(genres: List<TvShowEntity>)

    @Query("SELECT * FROM TvShows")
    suspend fun readTvShows(): List<TvShowEntity>

    @Query("SELECT * FROM TVSHOWS WHERE id = :id")
    suspend fun readTvShowById(id: Int): TvShowEntity

    @Query("DELETE FROM TvShows")
    suspend fun clearTvShows()

    /**
     IMAGES
     **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(img: ImageEntity)

    @Query("SELECT * FROM Images")
    suspend fun readImage(): ImageEntity

    /**
    FAVORITES
     **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorites(favorites: FavoritesEntity)

    @Query("SELECT * FROM Favorites")
    suspend fun readFavorites(): List<FavoritesEntity>

    @Query("SELECT * FROM Favorites WHERE id = :id")
    suspend fun readFavoriteById(id: Int): FavoritesEntity

    @Query("DELETE FROM Favorites WHERE id = :id")
    suspend fun deleteFavorite(id: Int)
}