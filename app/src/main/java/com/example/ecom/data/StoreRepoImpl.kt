package com.example.ecom.data

import android.util.Log
import com.example.ecom.data.local.entities.CartEntity
import com.example.ecom.data.local.Database
import com.example.ecom.data.local.entities.UserEntity
import com.example.ecom.data.local.entities.WishItemEntity
import com.example.ecom.data.local.relations.UserWithCarts
import com.example.ecom.data.local.relations.UserWithWishItems
import com.example.ecom.data.network.StoreApiService
import com.example.ecom.data.network.toProduct
import com.example.ecom.domain.StoreRepository
import com.example.ecom.domain.models.Product
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import retrofit2.HttpException


class StoreRepoImpl(private val apiService: StoreApiService,
                    private val db: Database): StoreRepository {
    override suspend fun getAllProducts(): List<Product> {
        return try {
            apiService.getAllProducts().map {
                it.toProduct()
            }
        } catch (e: HttpException) {
            Log.e("httpException", e.toString())
            emptyList()
        }
        catch (e: Exception) {
            Log.e("Expeption", e.toString())
            emptyList()
        }
    }

    override suspend fun getCategories(): List<String> {
        return try {
            apiService.getCategories()
        } catch (e: HttpException) {
            Log.e("httpException", e.toString())
            emptyList()
        } catch (e: Exception) {
            Log.e("Expeption", e.toString())
            emptyList()
        }
    }

    override suspend fun getProductsOfCategory(category: String): List<Product> {
        return try {
            apiService.getProductsOfCategory(category).map {
                it.toProduct()
            }
        } catch (e: HttpException) {
            Log.e("httpException", e.toString())
            emptyList()
        } catch (e: Exception) {
            Log.e("Expeption", e.toString())
            emptyList()
        }

    }

    override suspend fun getAllCartEntities(id: Int): List<UserWithCarts> {
        return try {
            db.userDao().getUserWithCarts(id)
        } catch (e: Exception) {
            Log.e("Expeption", e.toString())
            emptyList()
        }
    }

    override suspend fun getCartEntity(id: Int): CartEntity {
        return db.cartDao().getCartEntity(id)

    }

    override fun getAllCartEntitiesFlow(): Flow<List<CartEntity>> {
        return try {
            db.cartDao().getAllCartEntitiesFlow()
        } catch (e: Exception) {
            Log.e("Exception", e.toString())
            emptyFlow()
        }
    }

    override fun getUserWithCartsFlow(id: Int): Flow<UserWithCarts> {
        return try {
            db.userDao().getUserWithCartsFlow(id)
        } catch (e: Exception) {
            Log.e("Exception", e.toString())
            emptyFlow()
        }
    }

    override suspend fun insertWishItem(wishItemEntity: WishItemEntity) {
        db.wishItemDao().insertWishItem(wishItemEntity)
    }

    override fun getUserWithWishItemsFlow(id: Int): Flow<UserWithWishItems> {
        return try {
            db.userDao().getUserWithWishItemsFlow(id)
        } catch (e: Exception) {
            Log.e("Exception", e.toString())
            emptyFlow()
        }
    }

    override suspend fun deleteWishItem(wishItemEntity: WishItemEntity) {
        db.wishItemDao().deleteWithItem(wishItemEntity)
    }

    override suspend fun user(id: Int): UserEntity {
        return db.userDao().user(id)
    }

    override suspend fun logout() {
        Log.i("problem2","RUNNING?")
        logoutChannel.send(Unit)
        Log.i("problem3","RUNNING?")
    }

    override suspend fun getUserWithWishItems(id: Int): List<UserWithWishItems> {
        return db.userDao().getUserWithWishItems(id)
    }

    override val logoutChannel: Channel<Unit> = Channel()

    override suspend fun deleteCart(cart: CartEntity) {
        db.cartDao().delete(cart)
    }

    override suspend fun getProduct(id: Int): Product? {
        return try {
            apiService.getProduct(id).toProduct()
        } catch (e: Exception) {
            Log.e("Exception", e.toString())
            null
        }
    }

    override suspend fun getAllUsers(): List<UserEntity> {
        return db.userDao().getAllUsers()
    }

    override suspend fun insertUser(user: UserEntity) {
        db.userDao().insertUser(user)
    }

    override suspend fun insertCart(cart: CartEntity) {
        db.cartDao().insertCart(cart)
    }

    override suspend fun updateCart(cart: CartEntity) {
        db.cartDao().update(cart)
    }
}