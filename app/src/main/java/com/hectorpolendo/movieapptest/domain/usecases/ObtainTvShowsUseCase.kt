package com.hectorpolendo.movieapptest.domain.usecases

import com.hectorpolendo.movieapptest.data.Repository
import com.hectorpolendo.movieapptest.data.database.entities.toDatabase
import com.hectorpolendo.movieapptest.domain.models.TvShow
import javax.inject.Inject

class ObtainTvShowsUseCase @Inject constructor(
    private val repository: Repository
){
    suspend operator fun invoke(): List<TvShow>?{
        val tvShow = repository.getTvShowFromApi()
        return if(tvShow.isNotEmpty()){
            repository.clearTvShows()
            repository.insertTvShows(tvShow.map { it.toDatabase() })
            tvShow
        }else{
            repository.getTvShowFromDatabase()
        }
    }
}