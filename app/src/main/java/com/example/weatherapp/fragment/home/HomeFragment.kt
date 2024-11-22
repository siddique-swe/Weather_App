package com.example.weatherapp.fragment.home

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.weatherapp.data.CurrentLocation
import com.example.weatherapp.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val weatherDataAdapter = WeatherDataAdapter(
        onLocationClicked = { showLocationOption()}
    )
    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted ->
        if(isGranted){
            getCurrentLocation()
        }else {
            Toast.makeText(requireContext(),"location permission denied", Toast.LENGTH_SHORT).show()
        }


    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWeatherDateAdapter()
        setWeatherDate()
    }

    private fun setWeatherDateAdapter(){
        binding.weatherDataRecyclerView.adapter = weatherDataAdapter
    }
    private fun setWeatherDate(){
        weatherDataAdapter.setData(data = listOf(CurrentLocation(date = getCurrentDate())))
    }

    private fun getCurrentDate(): String{
        val currentDate = Date()
        val formatter = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
        return "Today,${formatter.format(currentDate)}"
    }
    private fun getCurrentLocation(){
        Toast.makeText(requireContext(),"getCurrentLocation()", Toast.LENGTH_SHORT).show()
    }
    private fun isLocationPermissionGranted(): Boolean{
        return ContextCompat.checkSelfPermission(
            requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
    private fun requestLocationPermission(){
        locationPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun proceedWithCurrentLocation(){
        if(isLocationPermissionGranted()){
            getCurrentLocation()
        }else{
            requestLocationPermission()
        }
    }

    private fun showLocationOption(){
        val options = arrayOf("Current location", "search Manually")
        android.app.AlertDialog.Builder(requireContext()).apply {
            setTitle("Choose Location Method")
            setItems(options){ _, which ->
                when(which) {
                    0 -> proceedWithCurrentLocation()
                }
            }
            show()
        }
    }
}