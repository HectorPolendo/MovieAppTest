package com.hectorpolendo.movieapptest.domain.usecases

import com.hectorpolendo.movieapptest.data.Repository
import com.hectorpolendo.movieapptest.data.database.entities.toDatabase
import com.hectorpolendo.movieapptest.domain.models.Genre
import javax.inject.Inject

class ObtainGenresUseCase @Inject constructor(
    private val repository: Repository
){
    suspend operator fun invoke(): List<Genre>?{
        val genres = repository.getGenresFromApi()
        return if(genres.isNotEmpty()){
            repository.clearGenres()
            repository.insertAllGenres(genres.map { it.toDatabase() })
            genres
        }else{
            repository.getGenresFromDatabase()
        }
    }
}