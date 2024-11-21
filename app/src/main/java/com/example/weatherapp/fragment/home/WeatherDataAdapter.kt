package com.example.weatherapp.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.CurrentLocation
import com.example.weatherapp.data.WeatherData
import com.example.weatherapp.databinding.ItemContainerCurrentLocationBinding

class WeatherDataAdapter(
    private val onLocationClicked:() -> Unit
) : RecyclerView.Adapter<WeatherDataAdapter.CurrentLocationViewHolder>()  {

    private val weatherData = mutableListOf<WeatherData>()

    fun setData(data : List<WeatherData>){
        weatherData.clear()
        weatherData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentLocationViewHolder {
        return CurrentLocationViewHolder(
            ItemContainerCurrentLocationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
      return weatherData.size
    }

    override fun onBindViewHolder(holder: CurrentLocationViewHolder, position: Int) {
        holder.bind(weatherData[position] as CurrentLocation)
    }

    //inner class
    inner class CurrentLocationViewHolder(
        private val binding: ItemContainerCurrentLocationBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(currentLocation: CurrentLocation){
            with(binding){
                textCurrentDate.text = currentLocation.date
                textCurrentLocation.text = currentLocation.location
                imageCurrentLocation.setOnClickListener {onLocationClicked()}
                textCurrentLocation.setOnClickListener {onLocationClicked() }

            }
        }
    }
}