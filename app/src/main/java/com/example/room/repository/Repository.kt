package com.example.room.repository

import com.example.room.api.RetrofitInstance
import com.example.room.model.Cities
import com.example.room.model.Countries
import com.example.room.model.Restaurants

class Repository {

    suspend fun getCities(): Cities {
        return RetrofitInstance.api.getCities()
    }

    suspend fun getCountries(): Countries {
        return RetrofitInstance.api.getCountries()
    }

    suspend fun getRestaurants(): Restaurants {
        return RetrofitInstance.api.getRestaurants()
    }

}