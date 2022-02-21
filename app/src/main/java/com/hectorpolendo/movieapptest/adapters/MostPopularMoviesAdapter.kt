package com.hectorpolendo.movieapptest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hectorpolendo.movieapptest.databinding.HorizontalItemBinding
import com.hectorpolendo.movieapptest.domain.models.Movie
import com.hectorpolendo.movieapptest.util.Constants

class MostPopularMoviesAdapter(): RecyclerView.Adapter<MostPopularMoviesAdapter.ViewHolder>() {
    private var moviesList = ArrayList<Movie>()
    lateinit var onItemClick:((Movie)-> Unit)

    fun setMovies(moviesList: ArrayList<Movie>){
        this.moviesList = moviesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(HorizontalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(Constants.PATH_IMGS+moviesList[position].poster_path)
            .into(holder.binding.ivImage)

        holder.binding.tvTitle.setText(moviesList[position].title)
        holder.binding.tvDate.setText(moviesList[position].release_date)

        holder.itemView.setOnClickListener{
            onItemClick.invoke(moviesList[position])
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    class ViewHolder(val binding: HorizontalItemBinding): RecyclerView.ViewHolder(binding.root)

}