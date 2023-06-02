package com.example.ecom.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("wish_item")
data class WishItemEntity (
    @PrimaryKey
    @ColumnInfo(name = "pid")
    val pid: Int,
    val userCreatorId: Int
)