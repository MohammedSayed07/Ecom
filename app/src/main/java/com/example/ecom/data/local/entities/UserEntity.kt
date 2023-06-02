package com.example.ecom.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("users")
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    val userId: Int,
    val email: String,
    val password: String
)