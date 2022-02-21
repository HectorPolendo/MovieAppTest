package com.hectorpolendo.movieapptest.ui.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hectorpolendo.movieapptest.adapters.MostPopularMoviesAdapter
import com.hectorpolendo.movieapptest.adapters.TvShowsAdapter
import com.hectorpolendo.movieapptest.databinding.FragmentHomeBinding
import com.hectorpolendo.movieapptest.domain.models.Movie
import com.hectorpolendo.movieapptest.domain.models.TvShow
import com.hectorpolendo.movieapptest.ui.view.activities.MovieActivity
import com.hectorpolendo.movieapptest.ui.view.activities.UserActivity
import com.hectorpolendo.movieapptest.ui.viewmodel.HomeViewModel
import com.hectorpolendo.movieapptest.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var mostPopularMoviesAdapter: MostPopularMoviesAdapter
    private lateinit var tvShowsAdapter: TvShowsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.onCreate()
        mostPopularMoviesAdapter = MostPopularMoviesAdapter()
        tvShowsAdapter = TvShowsAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMostPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = mostPopularMoviesAdapter
        }

        binding.rvTvShows.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = tvShowsAdapter
        }

        mostPopularMoviesAdapter.onItemClick = {
            Constants.FROM = "POPULAR"
            startActivity(
                Intent(activity, MovieActivity::class.java)
                    .putExtra(Constants.MOVIE_ID, it.id)
                    .putExtra(Constants.MOVIE_NAME, it.title)
                    .putExtra(Constants.MOVIE_IMG, it.poster_path))
        }

        tvShowsAdapter.onItemClick = {
            Constants.FROM = "SERIES"
            startActivity(
                Intent(activity, MovieActivity::class.java)
                    .putExtra(Constants.MOVIE_ID, it.id)
                    .putExtra(Constants.MOVIE_NAME, it.name)
                    .putExtra(Constants.MOVIE_IMG, it.poster_path))
        }

        binding.ivUser.setOnClickListener{
            startActivity(Intent(activity, UserActivity::class.java))
        }

        subscribeObservers()
    }

    fun subscribeObservers(){
        homeViewModel.movie.observe(this, {
            mostPopularMoviesAdapter.setMovies(it as ArrayList<Movie>)
        })

        homeViewModel.tvShow.observe(this,{
            tvShowsAdapter.setTvShows(it as ArrayList<TvShow>)
        })
    }

}