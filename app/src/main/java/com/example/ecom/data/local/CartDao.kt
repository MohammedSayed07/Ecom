package com.example.ecom.data.local

import androidx.room.*
import com.example.ecom.data.local.entities.CartEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface CartDao {
    @Insert
    suspend fun insertCart(cart: CartEntity)

    @Query("SELECT * FROM cart")
    suspend fun getAllCartEntities(): List<CartEntity>

    @Query("SELECT * FROM cart")
    fun getAllCartEntitiesFlow(): Flow<List<CartEntity>>

    @Query("SELECT * FROM cart WHERE pid = :id ")
    suspend fun getCartEntity(id: Int): CartEntity
    @Update
    suspend fun update(cart: CartEntity)

    @Delete
    suspend fun delete(cart: CartEntity)
}