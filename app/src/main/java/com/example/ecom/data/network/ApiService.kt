package com.example.ecom.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface StoreApiService {
    @GET("/products")
    suspend fun getAllProducts():
            List<ProductDto>

    @GET("/products/categories")
    suspend fun getCategories():
            List<String>

    @GET("/products/category/{category}")
    suspend fun getProductsOfCategory(@Path("category") category: String):
            List<ProductDto>

    @GET("/products/{id}")
    suspend fun getProduct(@Path("id") id: Int):
            ProductDto


}