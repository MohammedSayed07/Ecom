package com.example.ecom.data.local

import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@androidx.room.Database(entities = [CartEntity::class], version = 1, exportSchema = false)
abstract class Database: RoomDatabase() {
    abstract fun cartDao(): CartDao

}
