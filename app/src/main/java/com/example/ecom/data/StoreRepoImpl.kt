package com.example.ecom.data

import android.util.Log
import com.example.ecom.domain.StoreRepository
import retrofit2.HttpException


class StoreRepoImpl(private val apiService: StoreApiService): StoreRepository {
    override suspend fun getAllProducts(): List<Product> {
        return try {
            apiService.getAllProducts()
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
        return apiService.getCategories()
    }

    override suspend fun getProductsOfCategory(category: String): List<Product> {
        return apiService.getProductsOfCategory(category)

    }
}