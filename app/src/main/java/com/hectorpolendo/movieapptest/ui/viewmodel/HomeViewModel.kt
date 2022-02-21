package com.hectorpolendo.movieapptest.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hectorpolendo.movieapptest.data.Repository
import com.hectorpolendo.movieapptest.domain.models.Movie
import com.hectorpolendo.movieapptest.domain.models.TvShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _movie = MutableLiveData<List<Movie>>()
    val movie: LiveData<List<Movie>> get() = _movie

    private val _tvShow = MutableLiveData<List<TvShow>>()
    val tvShow: LiveData<List<TvShow>> get() = _tvShow

    fun onCreate(){
        viewModelScope.launch {
            _movie.postValue(repository.getMostPopularFromDatabase())
            _tvShow.postValue(repository.getTvShowFromDatabase())
        }
    }
}