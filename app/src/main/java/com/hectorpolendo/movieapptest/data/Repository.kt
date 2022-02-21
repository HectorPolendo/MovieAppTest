package com.hectorpolendo.movieapptest.data

import com.hectorpolendo.movieapptest.data.database.MoviesDao
import com.hectorpolendo.movieapptest.data.database.entities.*
import com.hectorpolendo.movieapptest.data.network.ServiceController
import com.hectorpolendo.movieapptest.data.pojos.GenreResponse
import com.hectorpolendo.movieapptest.data.pojos.MostPopularResponse
import com.hectorpolendo.movieapptest.data.pojos.TvShowResponse
import com.hectorpolendo.movieapptest.domain.models.*
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: ServiceController,
    private val moviesAppDao: MoviesDao
) {

    /**
    MOST POPULAR
     **/

    suspend fun getMostPopularFromApi(): List<Movie>{
        val response: List<MostPopularResponse> = api.getMostPopular()
        return response.map { it.toDomain() }
    }

    suspend fun getMostPopularFromDatabase(): List<Movie>{
        val response: List<MostPopularEntity> = moviesAppDao.readMostPopular()
        return response.map { it.toDomain() }
    }

    suspend fun getMostPopularFromDatabaseById(id: Int): Movie{
        val response: MostPopularEntity = moviesAppDao.readMostPopularById(id)
        return response.toDomain()
    }

    suspend fun insertAllMostPopular(mostPopular: List<MostPopularEntity>){
        moviesAppDao.insertMostPopular(mostPopular)
    }

    suspend fun clearMostPopular(){
        moviesAppDao.clearMostPopular()
    }

    /**
    GENRES
     **/

    suspend fun getGenresFromApi(): List<Genre>{
        val response: List<GenreResponse> = api.getGenres()
        return response.map { it.toDomain() }
    }

    suspend fun getGenresFromDatabase(): List<Genre>{
        val response: List<GenreEntity> = moviesAppDao.readGenres()
        return response.map { it.toDomain() }
    }

    suspend fun insertAllGenres(genres: List<GenreEntity>){
        moviesAppDao.insertGenres(genres)
    }

    suspend fun clearGenres(){
        moviesAppDao.clearGenres()
    }

    /**
    TV SHOWS
     **/

    suspend fun getTvShowFromApi(): List<TvShow>{
        val response: List<TvShowResponse> = api.getTvShows()
        return response.map { it.toDomain() }
    }

    suspend fun getTvShowFromDatabase(): List<TvShow>{
        val response: List<TvShowEntity> = moviesAppDao.readTvShows()
        return response.map { it.toDomain() }
    }

    suspend fun getTvShowFromDatabaseById(id: Int): TvShow{
        val response: TvShowEntity = moviesAppDao.readTvShowById(id)
        return response.toDomain()
    }

    suspend fun insertTvShows(tvShows: List<TvShowEntity>){
        moviesAppDao.insertTvShows(tvShows)
    }

    suspend fun clearTvShows(){
        moviesAppDao.clearTvShows()
    }

    /**
    IMAGES
     **/

    suspend fun insertImage(img: String){
        val image = Images(1, img)

        moviesAppDao.insertImage(image.toDatabase())
    }

    suspend fun getImage(): String?{
        val response: ImageEntity? = moviesAppDao.readImage()
        if(response != null)
            return response.toDomain().image
        else{
            return null
        }
    }

    /**
    FAVORITES
     **/

    suspend fun getFavorites(): List<Movie>{
        val response: List<FavoritesEntity> = moviesAppDao.readFavorites()
        return response.map { it.toDomain() }
    }

    suspend fun getFavoriteByid(id: Int): Boolean{
        val response: FavoritesEntity? = moviesAppDao.readFavoriteById(id)
        return response != null
    }

    suspend fun insertFavorite(favorite: FavoritesEntity){
        moviesAppDao.insertFavorites(favorite)
    }

    suspend fun deleteFavorite(id: Int){
        moviesAppDao.deleteFavorite(id)
    }
}