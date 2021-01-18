package com.example.room.repository

import androidx.lifecycle.LiveData
import com.example.room.data.UserDao
import com.example.room.model.User

class UserRepository(private  val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    fun correctPassword(email: String): String{
        return userDao.correctPassword(email)
    }

    fun thisPersonId(email: String): Int {
        return userDao.thisPersonId(email)
    }

    fun thisPersonAddress(id: Int): String {
        return userDao.thisPersonAddress(id)
    }

    fun thisPersonPhone(id: Int): String {
        return userDao.thisPersonPhone(id)
    }

    fun thisPersonMail(id: Int): String {
        return userDao.thisPersonMail(id)
    }

    fun thisPersonFirstName(id: Int): String {
        return userDao.thisPersonFirstName(id)
    }

    fun thisPersonLastName(id: Int): String {
        return userDao.thisPersonLastName(id)
    }

}