package com.example.room.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "restaurant")
@Parcelize
data class Restaurant(
    val address: String,
    val area: String,
    val city: String,
    val country: String,
    @PrimaryKey
    val id: Int,
    val image_url: String,
    val lat: Double,
    val lng: Double,
    val mobile_reserve_url: String,
    val name: String,
    val phone: String,
    val postal_code: String,
    val price: Int,
    val reserve_url: String,
    val state: String
) : Parcelable