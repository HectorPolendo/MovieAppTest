package com.hectorpolendo.movieapptest.ui.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hectorpolendo.movieapptest.data.Repository
import com.hectorpolendo.movieapptest.domain.models.Genre
import com.hectorpolendo.movieapptest.domain.models.Movie
import com.hectorpolendo.movieapptest.domain.models.TvShow
import com.hectorpolendo.movieapptest.domain.usecases.ObtainGenresUseCase
import com.hectorpolendo.movieapptest.domain.usecases.ObtainMostPopularUseCase
import com.hectorpolendo.movieapptest.domain.usecases.ObtainTvShowsUseCase
import com.hectorpolendo.movieapptest.util.Methods
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val mostPopularUseCase: ObtainMostPopularUseCase,
    private val genresUseCase: ObtainGenresUseCase,
    private val tvShowsUseCase: ObtainTvShowsUseCase,
    private val repository: Repository
): ViewModel(){

    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> get() = _progress

    private val _mostPopular = MutableLiveData<List<Movie>>()
    private val _genres = MutableLiveData<List<Genre>>()
    private val _tvShow = MutableLiveData<List<TvShow>>()

    @RequiresApi(Build.VERSION_CODES.M)
    fun onCreate(context: Context){
        viewModelScope.launch {
            if(Methods.isOnline(context)){
                _progress.postValue(0)
                val mostPopularResult = mostPopularUseCase()
                if(!mostPopularResult.isNullOrEmpty()){
                    _mostPopular.postValue(mostPopularResult!!)
                }
                _progress.postValue(33)
                val genreResult = genresUseCase()
                if(!genreResult.isNullOrEmpty()){
                    _genres.postValue(genreResult!!)
                }
                _progress.postValue(66)
                val tvShowResult = tvShowsUseCase()
                if(!tvShowResult.isNullOrEmpty()){
                    _tvShow.postValue(tvShowResult!!)
                }
                _progress.postValue(100)
            }else{
                _progress.postValue(0)
                _mostPopular.postValue(repository.getMostPopularFromDatabase())
                _progress.postValue(33)
                _genres.postValue(repository.getGenresFromDatabase())
                _progress.postValue(66)
                _tvShow.postValue(repository.getTvShowFromDatabase())
                _progress.postValue(100)
            }

        }
    }
}