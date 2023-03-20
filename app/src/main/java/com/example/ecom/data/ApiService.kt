package com.example.ecom.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StoreApiService {
    @GET("/products")
    suspend fun getAllProducts():
            List<Product>

    @GET("/products/categories")
    suspend fun getCategories():
            List<String>

    @GET("/products/category/{category}")
    suspend fun getProductsOfCategory(@Path("category") category: String):
            List<Product>
}