package com.hectorpolendo.movieapptest.ui.view.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.hectorpolendo.movieapptest.R
import com.hectorpolendo.movieapptest.databinding.ActivityMovieBinding
import com.hectorpolendo.movieapptest.domain.models.Movie
import com.hectorpolendo.movieapptest.domain.models.TvShow
import com.hectorpolendo.movieapptest.ui.viewmodel.MovieViewModel
import com.hectorpolendo.movieapptest.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBinding
    private val movieViewModel: MovieViewModel by viewModels()
    private var movieId: Int? = null
    private var movieName: String? = null
    private var movieImg: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loading()
        getInformation()
        movieViewModel.onCreate(movieId!!)
        subscribeObservers()

        binding.fbFavorite.setOnClickListener {
            movieViewModel.changeFavorite(movieId!!)
        }

    }

    private fun subscribeObservers() {
        movieViewModel.movie.observe(this, object : Observer<Movie> {
            override fun onChanged(t: Movie?) {
                Glide.with(this@MovieActivity)
                    .load(Constants.PATH_IMGS+t!!.poster_path)
                    .into(binding.ivMovie)

                binding.collapsingToolbar.title = t!!.title
                binding.collapsingToolbar.setExpandedTitleColor(Color.WHITE)
                binding.collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE)


                binding.tvSynopsis.setText(t!!.overview)
                onResponseCase()
            }
        })

        movieViewModel.tvShow.observe(this, object : Observer<TvShow> {
            override fun onChanged(t: TvShow?) {
                Glide.with(this@MovieActivity)
                    .load(Constants.PATH_IMGS+t!!.poster_path)
                    .into(binding.ivMovie)

                binding.collapsingToolbar.title = t!!.name
                binding.collapsingToolbar.setExpandedTitleColor(Color.WHITE)
                binding.collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE)

                binding.tvSynopsis.setText(t!!.overview)
                onResponseCase()
            }
        })

        movieViewModel.isFavorite.observe(this, {
            if(it){
                binding.fbFavorite.setImageResource(R.drawable.ic_is_favorite)
            }else{
                binding.fbFavorite.setImageResource(R.drawable.ic_favorite)
            }
        })

    }

    private fun getInformation() {
        movieId = intent.getIntExtra(Constants.MOVIE_ID, 0)!!
        movieName = intent.getStringExtra(Constants.MOVIE_NAME)!!
        movieImg = intent.getStringExtra(Constants.MOVIE_IMG)!!
    }

    private fun loading(){
        with(binding){
            progressBar.visibility = View.VISIBLE
            tvSynopsis.visibility = View.INVISIBLE
            tvDuration.visibility = View.INVISIBLE
            tvRating.visibility = View.INVISIBLE
            tvYear.visibility = View.INVISIBLE
            if(Constants.FROM == "SERIES"){
                binding.fbFavorite.visibility = View.GONE
            }
        }
    }

    private fun onResponseCase(){
        with(binding){
            progressBar.visibility = View.INVISIBLE
            tvSynopsis.visibility = View.VISIBLE
            tvDuration.visibility = View.VISIBLE
            tvRating.visibility = View.VISIBLE
            tvYear.visibility = View.VISIBLE
        }
    }
}