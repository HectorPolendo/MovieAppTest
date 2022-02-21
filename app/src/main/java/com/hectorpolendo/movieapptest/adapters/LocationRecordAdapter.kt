package com.hectorpolendo.movieapptest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hectorpolendo.movieapptest.databinding.LocationItemBinding
import com.hectorpolendo.movieapptest.domain.models.LocationRecord

class LocationRecordAdapter: RecyclerView.Adapter<LocationRecordAdapter.ViewHolder>() {
    private var locationRedords = ArrayList<LocationRecord>()

    fun setLocations(locationRedords: ArrayList<LocationRecord>){
        this.locationRedords = locationRedords
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LocationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvDate.setText(locationRedords[position].date)
        holder.binding.tvLatitude.setText(locationRedords[position].latitude)
        holder.binding.tvLongitude.setText(locationRedords[position].longitude)
    }

    override fun getItemCount(): Int {
        return locationRedords.size
    }

    class ViewHolder(val binding: LocationItemBinding): RecyclerView.ViewHolder(binding.root)
}