package com.example.room

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import retrofit2.Response
import com.example.room.model.Cities
import com.example.room.model.Countries
import com.example.room.model.Restaurant
import com.example.room.model.Restaurants
import com.example.room.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    val myResponseCities: MutableLiveData<Cities> = MutableLiveData()
    val myResponseCountries: MutableLiveData<Countries> = MutableLiveData()
    val myResponseRestaurants: MutableLiveData<Restaurants> = MutableLiveData()

    val myResponseAll:MutableLiveData<Response<Restaurants>> = MutableLiveData()

    val restaurantList: MutableList<Restaurant> = mutableListOf()

    fun getCities() {
        viewModelScope.launch {
            val response: Cities = repository.getCities()
            myResponseCities.value = response
        }
    }

    fun getCountries() {
        viewModelScope.launch {
            val response: Countries = repository.getCountries()
            myResponseCountries.value = response
        }
    }

    fun getRestaurants() {
        viewModelScope.launch {
            val response: Restaurants = repository.getRestaurants()
            myResponseRestaurants.value = response
        }
    }

    fun getAllRestaurant(country:String, page:Int){
        viewModelScope.launch {
            val response = repository.getAllRestaurants(country, page)
            myResponseAll.value = response
        }
    }

}