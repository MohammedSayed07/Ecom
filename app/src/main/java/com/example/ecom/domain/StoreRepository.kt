package com.example.ecom.domain

import com.example.ecom.data.Product
import kotlinx.coroutines.flow.Flow

interface StoreRepository {

    suspend fun getAllProducts(): List<Product>
    suspend fun getCategories(): List<String>
    suspend fun getProductsOfCategory(category: String): List<Product>
}