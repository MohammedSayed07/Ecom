package com.example.ecom.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.example.ecom.data.local.entities.WishItemEntity

@Dao
interface WishItemDao {
    @Insert
    suspend fun insertWishItem(wishItemEntity: WishItemEntity)

    @Delete
    suspend fun deleteWithItem(wishItemEntity: WishItemEntity)
}