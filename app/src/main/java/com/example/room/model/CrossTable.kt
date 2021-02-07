package com.example.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite")

data class CrossTable (
        @PrimaryKey(autoGenerate = true)
        val favoriteID: Int,
        val id: Int,
        val userID: Int
)