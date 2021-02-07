package com.example.room.repository

import retrofit2.Response
import com.example.room.api.RetrofitInstance
import com.example.room.model.Cities
import com.example.room.model.Countries
import com.example.room.model.Restaurants

class Repository {

    suspend fun getCities(): Cities {
        return RetrofitInstance.api.getCities()
    }

    suspend fun getRestaurants(country:String, page:Int): Response<Restaurants> {
        return RetrofitInstance.api.getAllRestaurants(country, page)
    }

}