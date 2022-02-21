package com.hectorpolendo.movieapptest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hectorpolendo.movieapptest.databinding.FavoritesItemBinding
import com.hectorpolendo.movieapptest.domain.models.Movie
import com.hectorpolendo.movieapptest.util.Constants

class FavoritesAdapter(): RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {
    private var favoriteList = ArrayList<Movie>()
    lateinit var onItemClick:((Movie)-> Unit)

    fun setFavorites(favoriteList: ArrayList<Movie>){
        this.favoriteList = favoriteList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FavoritesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(Constants.PATH_IMGS+favoriteList[position].poster_path)
            .into(holder.binding.ivMovie)

        holder.itemView.setOnClickListener{
            onItemClick.invoke(favoriteList[position])
        }
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    class ViewHolder(val binding: FavoritesItemBinding): RecyclerView.ViewHolder(binding.root)

}