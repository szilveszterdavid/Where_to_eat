package com.example.room.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.room.data.UserDatabase
import com.example.room.model.CrossTable
import com.example.room.model.Restaurant
import com.example.room.model.User
import com.example.room.repository.UserRepository

import kotlinx.coroutines.*

class DaoViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<User>>
    val readAllRestaurant: LiveData<List<Restaurant>>
    val readAllCross:LiveData<List<CrossTable>>
    private val repository: UserRepository

    init {
        val restaurantDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(restaurantDao)

        readAllData = repository.readAllData
        readAllRestaurant = repository.readAllRestaurant
        readAllCross = repository.readAllCrossTable
    }

    fun addRestaurantDB(restaurant: Restaurant) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRestaurant(restaurant)
        }
    }

    fun addUserRestaurantDB(crossTable: CrossTable) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCrossTable(crossTable)
        }
    }

    fun deleteCrossDB(id: Int, userID:Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCrossTable(id, userID)
        }
    }

}