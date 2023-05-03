package com.example.ecom.domain

import com.example.ecom.data.local.CartEntity
import com.example.ecom.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface StoreRepository {

    suspend fun getAllProducts(): List<Product>
    suspend fun getCategories(): List<String>
    suspend fun getProductsOfCategory(category: String): List<Product>
    suspend fun getAllCartEntities(): List<CartEntity>
    suspend fun insertCart(cart: CartEntity)
    suspend fun getProduct(id: Int): Product?
    suspend fun updateCart(cart: CartEntity)
    suspend fun getCartEntity(id: Int): CartEntity
    fun getAllCartEntitiesFlow(): Flow<List<CartEntity>>
    suspend fun deleteCart(cart: CartEntity)

}