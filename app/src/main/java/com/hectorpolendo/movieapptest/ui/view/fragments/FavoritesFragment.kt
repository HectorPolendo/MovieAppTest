package com.hectorpolendo.movieapptest.ui.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.hectorpolendo.movieapptest.adapters.FavoritesAdapter
import com.hectorpolendo.movieapptest.databinding.FragmentFavoritesBinding
import com.hectorpolendo.movieapptest.domain.models.Movie
import com.hectorpolendo.movieapptest.ui.view.activities.MovieActivity
import com.hectorpolendo.movieapptest.ui.viewmodel.FavoritesViewModel
import com.hectorpolendo.movieapptest.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoritesViewModel.onCreate()
        favoritesAdapter = FavoritesAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(activity,2)
            adapter = favoritesAdapter
        }

        favoritesAdapter.onItemClick = {
            Constants.FROM = "FAVORITES"
            startActivity(
                Intent(activity, MovieActivity::class.java)
                    .putExtra(Constants.MOVIE_ID, it.id)
                    .putExtra(Constants.MOVIE_NAME, it.title)
                    .putExtra(Constants.MOVIE_IMG, it.poster_path))
        }

        subscribeObservers()
    }

    fun subscribeObservers(){
        favoritesViewModel.favorites.observe(this, {
            favoritesAdapter.setFavorites(it as ArrayList<Movie>)
            if(it.isEmpty()){
                binding.rvFavorites.visibility = View.GONE
                binding.constContainer.visibility = View.VISIBLE
            }else{
                binding.rvFavorites.visibility = View.VISIBLE
                binding.constContainer.visibility = View.GONE
            }
        })
    }
}