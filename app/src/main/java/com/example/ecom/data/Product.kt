package com.example.ecom.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val category: String,
    val description: String,
    val id: Int,
    val imageUrl: String,
    val price: Double,
    val rating: Rating,
    val title: String
) : Parcelable