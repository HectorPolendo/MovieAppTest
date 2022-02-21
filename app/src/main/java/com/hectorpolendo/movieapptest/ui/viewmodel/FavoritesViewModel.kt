package com.hectorpolendo.movieapptest.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hectorpolendo.movieapptest.data.Repository
import com.hectorpolendo.movieapptest.domain.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _favorites = MutableLiveData<List<Movie>>()
    val favorites: LiveData<List<Movie>> get() = _favorites

    fun onCreate(){
        viewModelScope.launch {
            _favorites.postValue(repository.getFavorites())
        }
    }
}