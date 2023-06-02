package com.example.ecom.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecom.data.local.entities.UserEntity
import com.example.ecom.domain.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val storeRepository: StoreRepository
): ViewModel() {


    fun insertUser(user: UserEntity) {
        viewModelScope.launch {
            storeRepository.insertUser(user)
        }
    }
}