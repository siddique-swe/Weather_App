package com.example.weatherapp.fragment.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.RemoteLocation
import com.example.weatherapp.network.repository.WeatherDataRepository
import kotlinx.coroutines.launch

class LocationViewModel ( private val weatherDataRepository: WeatherDataRepository): ViewModel(){

    private val _searchResult = MutableLiveData<SearchResultDataState>()
    val searchResult: LiveData<SearchResultDataState> get() = _searchResult

    fun searchLocation(query: String){
        viewModelScope.launch {
            emitSearchResultState(isLoading = true)
            val searchResult = weatherDataRepository.searchLocation(query)
            if(searchResult.isNullOrEmpty()){
                emitSearchResultState(error = "Location not found try again.")
            }else{
                emitSearchResultState(locations = searchResult)
            }
        }
    }

    private fun emitSearchResultState(
        isLoading: Boolean = false,
        locations: List<RemoteLocation>? = null,
        error: String? = null
    ){
        val searchResultDataState = SearchResultDataState(isLoading, locations, error)
        _searchResult.value = searchResultDataState
    }

    data class SearchResultDataState(
        val isLoading : Boolean,
        val locations : List<RemoteLocation>?,
        val error: String?
    )

}