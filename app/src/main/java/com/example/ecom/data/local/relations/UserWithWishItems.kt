package com.example.ecom.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.ecom.data.local.entities.UserEntity
import com.example.ecom.data.local.entities.WishItemEntity

data class UserWithWishItems(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userCreatorId"
    )
    val wishItems: List<WishItemEntity>
)
