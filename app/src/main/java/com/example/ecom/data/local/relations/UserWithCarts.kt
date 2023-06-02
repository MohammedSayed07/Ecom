package com.example.ecom.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.ecom.data.local.entities.CartEntity
import com.example.ecom.data.local.entities.UserEntity

data class UserWithCarts (
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userCreatorId"
    )
    val carts: List<CartEntity>
)