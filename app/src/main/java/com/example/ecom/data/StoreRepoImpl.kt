package com.example.ecom.data

import android.util.Log
import com.example.ecom.data.local.CartEntity
import com.example.ecom.data.local.Database
import com.example.ecom.data.network.StoreApiService
import com.example.ecom.data.network.toProduct
import com.example.ecom.domain.StoreRepository
import com.example.ecom.domain.models.Product
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

    override suspend fun getAllCartEntities(): List<CartEntity> {
        return try {
            db.cartDao().getAllCartEntities()
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

    override suspend fun insertCart(cart: CartEntity) {
        db.cartDao().insertCart(cart)
    }

    override suspend fun updateCart(cart: CartEntity) {
        db.cartDao().update(cart)
    }
}