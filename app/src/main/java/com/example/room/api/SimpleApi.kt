package com.example.room.api

import com.example.room.model.Cities
import com.example.room.model.Countries
import com.example.room.model.Restaurants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface SimpleApi {
    @GET("cities")
    suspend fun getCities(): Cities

    @GET("countries")
    suspend fun getCountries(): Countries

    @GET("restaurants")
    suspend fun getRestaurants(): Restaurants
}