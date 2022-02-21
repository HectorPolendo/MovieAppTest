package com.hectorpolendo.movieapptest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hectorpolendo.movieapptest.databinding.HorizontalItemBinding
import com.hectorpolendo.movieapptest.domain.models.TvShow
import com.hectorpolendo.movieapptest.util.Constants

class TvShowsAdapter: RecyclerView.Adapter<TvShowsAdapter.ViewHolder>() {
    private var tvShowsList = ArrayList<TvShow>()
    lateinit var onItemClick:((TvShow)-> Unit)

    fun setTvShows(tvShowList: ArrayList<TvShow>){
        this.tvShowsList = tvShowList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(HorizontalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(Constants.PATH_IMGS+tvShowsList[position].poster_path)
            .into(holder.binding.ivImage)

        holder.binding.tvTitle.setText(tvShowsList[position].name)
        holder.binding.tvDate.setText(tvShowsList[position].first_air_date)

        holder.itemView.setOnClickListener{
            onItemClick.invoke(tvShowsList[position])
        }
    }

    override fun getItemCount(): Int {
        return tvShowsList.size
    }

    class ViewHolder(val binding: HorizontalItemBinding): RecyclerView.ViewHolder(binding.root)
}