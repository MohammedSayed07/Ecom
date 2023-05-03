package com.example.ecom.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartEntity (
    @PrimaryKey
    val pid: Int,
    val quantity: Int
)