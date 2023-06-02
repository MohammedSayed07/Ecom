package com.example.ecom.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.ecom.data.local.entities.UserEntity
import com.example.ecom.data.local.relations.UserWithCarts
import com.example.ecom.data.local.relations.UserWithWishItems
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(userEntity: UserEntity)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserEntity>

    @Query("SELECT * FROM users WHERE userId = :id")
    suspend fun user(id: Int): UserEntity

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :id")
    suspend fun getUserWithCarts(id: Int): List<UserWithCarts>

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :id")
    suspend fun getUserWithWishItems(id: Int): List<UserWithWishItems>

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :id")
    fun getUserWithCartsFlow(id: Int): Flow<UserWithCarts>

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :id")
    fun getUserWithWishItemsFlow(id: Int): Flow<UserWithWishItems>

}