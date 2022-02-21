package com.hectorpolendo.movieapptest.domain.usecases

import com.hectorpolendo.movieapptest.data.Repository
import com.hectorpolendo.movieapptest.data.database.entities.toDatabase
import com.hectorpolendo.movieapptest.domain.models.Movie
import javax.inject.Inject

class ObtainMostPopularUseCase @Inject constructor(
    private val repository: Repository
){
    suspend operator fun invoke(): List<Movie>?{
        val mostPopular = repository.getMostPopularFromApi()
        return if(mostPopular.isNotEmpty()){
            repository.clearMostPopular()
            repository.insertAllMostPopular(mostPopular.map { it.toDatabase() })
            mostPopular
        }else{
            repository.getMostPopularFromDatabase()
        }
    }

}