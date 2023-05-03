package com.example.ecom.data.network


data class ProductDto(
    val id: Int,
    val category: String,
    val description: String,
    val image: String,
    val price: Double,
    val rating: RatingDto,
    val title: String
)