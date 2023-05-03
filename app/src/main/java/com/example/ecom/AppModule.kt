package com.example.ecom

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.ecom.data.network.StoreApiService
import com.example.ecom.data.StoreRepoImpl
import com.example.ecom.data.local.Database
import com.example.ecom.domain.StoreRepository
import com.example.ecom.ui.cart.CartViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

private const val BASE_URL = "https://fakestoreapi.com"
private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideStoreApi(): StoreApiService {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()
            )
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(applicationContext: Application): Database {
        return  Room.databaseBuilder(
            applicationContext,
            Database::class.java, "database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideStoreRepository(storeApiService: StoreApiService, db: Database): StoreRepository {
        return StoreRepoImpl(storeApiService, db)
    }
}