package com.example.ecom.data.local

import androidx.room.RoomDatabase
import com.example.ecom.data.local.entities.CartEntity
import com.example.ecom.data.local.entities.UserEntity
import com.example.ecom.data.local.entities.WishItemEntity

@androidx.room.Database(entities = [CartEntity::class, UserEntity::class, WishItemEntity::class], version = 1, exportSchema = false)
abstract class Database: RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun userDao(): UserDao
    abstract fun wishItemDao(): WishItemDao

}
