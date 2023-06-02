package com.example.ecom.domain

import com.example.ecom.data.local.entities.CartEntity
import com.example.ecom.data.local.entities.UserEntity
import com.example.ecom.data.local.entities.WishItemEntity
import com.example.ecom.data.local.relations.UserWithCarts
import com.example.ecom.data.local.relations.UserWithWishItems
import com.example.ecom.domain.models.Product
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

interface StoreRepository {

    suspend fun getAllProducts(): List<Product>
    suspend fun getCategories(): List<String>
    suspend fun getProductsOfCategory(category: String): List<Product>
    suspend fun getAllCartEntities(id: Int): List<UserWithCarts>
    suspend fun insertCart(cart: CartEntity)
    suspend fun getProduct(id: Int): Product?
    suspend fun updateCart(cart: CartEntity)
    suspend fun getCartEntity(id: Int): CartEntity
    fun getAllCartEntitiesFlow(): Flow<List<CartEntity>>

    fun getUserWithCartsFlow(id: Int): Flow<UserWithCarts>
    suspend fun deleteCart(cart: CartEntity)
    suspend fun insertUser(user: UserEntity)
    suspend fun getAllUsers(): List<UserEntity>
    suspend fun insertWishItem(wishItemEntity: WishItemEntity)
    fun getUserWithWishItemsFlow(id: Int): Flow<UserWithWishItems>
    suspend fun getUserWithWishItems(id: Int): List<UserWithWishItems>
    suspend fun deleteWishItem(wishItemEntity: WishItemEntity)
    suspend fun user(id: Int): UserEntity
    suspend fun logout()
    val logoutChannel: Channel<Unit>



}