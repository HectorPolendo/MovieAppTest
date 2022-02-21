package com.hectorpolendo.movieapptest.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hectorpolendo.movieapptest.data.Repository
import com.hectorpolendo.movieapptest.data.database.entities.toDatabase
import com.hectorpolendo.movieapptest.data.database.entities.toDatabaseFav
import com.hectorpolendo.movieapptest.domain.models.Movie
import com.hectorpolendo.movieapptest.domain.models.TvShow
import com.hectorpolendo.movieapptest.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> get() = _movie

    private val _tvShow = MutableLiveData<TvShow>()
    val tvShow: LiveData<TvShow> get() = _tvShow

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun onCreate(id: Int){
        viewModelScope.launch {
            if(Constants.FROM == "SERIES"){
                _tvShow.postValue(repository.getTvShowFromDatabaseById(id))
            }else{
                _movie.postValue(repository.getMostPopularFromDatabaseById(id))
                if(repository.getFavoriteByid(id)){
                    _isFavorite.postValue(true)
                }else{
                    _isFavorite.postValue(false)
                }
            }
        }
    }

    fun changeFavorite(id: Int){
        viewModelScope.launch {
            if(repository.getFavoriteByid(id)){
                repository.deleteFavorite(id)
                _isFavorite.postValue(false)
            }else{
                repository.insertFavorite(_movie.value!!.toDatabaseFav())
                _isFavorite.postValue(true)
            }
        }
    }
}