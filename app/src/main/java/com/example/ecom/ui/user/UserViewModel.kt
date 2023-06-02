package com.example.ecom.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecom.domain.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val storeRepository: StoreRepository
): ViewModel() {
    private val _user = MutableLiveData<String>()
    val user: LiveData<String> get() = _user

    fun getUser(id: Int) {
        viewModelScope.launch {
            val userTemp = storeRepository.user(id)
            _user.value = userTemp.email
        }

    }

    fun logout() {
        viewModelScope.launch {
            withContext(NonCancellable) {
                storeRepository.logout()
            }

        }

    }
}