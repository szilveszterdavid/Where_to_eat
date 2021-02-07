package com.example.room.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import retrofit2.Response
import com.example.room.model.Cities
import com.example.room.model.Restaurants
import com.example.room.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    val myResponseCities: MutableLiveData<Cities> = MutableLiveData()

    val myResponseAll:MutableLiveData<Response<Restaurants>> = MutableLiveData()

    fun getCities() {
        viewModelScope.launch {
            val response: Cities = repository.getCities()
            myResponseCities.value = response
        }
    }

    fun getRestaurants(country:String, page:Int){
        viewModelScope.launch {
            val response = repository.getRestaurants(country, page)
            myResponseAll.value = response
        }
    }

}