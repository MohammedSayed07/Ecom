package com.example.ecom.ui.login

import androidx.lifecycle.ViewModel
import com.example.ecom.data.local.entities.UserEntity
import com.example.ecom.domain.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val storeRepository: StoreRepository
): ViewModel() {
    suspend fun getAllUsers(): List<UserEntity> {
        return storeRepository.getAllUsers()
    }
}