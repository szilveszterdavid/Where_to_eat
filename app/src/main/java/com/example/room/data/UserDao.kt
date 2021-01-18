package com.example.room.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.room.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query(value = "SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>

    @Query(value = "SELECT password FROM user_table where email = :email")
    fun correctPassword(email: String): String

    @Query(value = "SELECT id FROM user_table where email = :email")
    fun thisPersonId(email: String): Int

    @Query(value = "SELECT address FROM user_table where id = :id")
    fun thisPersonAddress(id: Int): String

    @Query(value = "SELECT phone FROM user_table where id = :id")
    fun thisPersonPhone(id: Int): String

    @Query(value = "SELECT email FROM user_table where id = :id")
    fun thisPersonMail(id: Int): String

    @Query(value = "SELECT firstName FROM user_table where id = :id")
    fun thisPersonFirstName(id: Int): String

    @Query(value = "SELECT lastName FROM user_table where id = :id")
    fun thisPersonLastName(id: Int): String
}