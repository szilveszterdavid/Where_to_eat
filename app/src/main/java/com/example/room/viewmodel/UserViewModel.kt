package com.example.room.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.room.data.UserDatabase
import com.example.room.repository.UserRepository
import com.example.room.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application):AndroidViewModel(application) {

    val readAllData: LiveData<List<User>>
    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(
            application
        ).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO){
            repository.addUser(user)
        }
    }

    fun correctPassword(email: String): String{
        return repository.correctPassword(email)
    }

    fun thisPersonId(email: String): Int {
        return repository.thisPersonId(email)
    }

    fun thisPersonAddress(id: Int): String {
        return repository.thisPersonAddress(id)
    }

    fun thisPersonPhone(id: Int): String {
        return repository.thisPersonPhone(id)
    }

    fun thisPersonMail(id: Int): String {
        return repository.thisPersonMail(id)
    }

    fun thisPersonFirstName(id: Int): String {
        return repository.thisPersonFirstName(id)
    }

    fun thisPersonLastName(id: Int): String {
        return repository.thisPersonLastName(id)
    }

}