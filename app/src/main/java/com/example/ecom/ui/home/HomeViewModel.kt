package com.example.ecom.ui.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecom.data.Product
import com.example.ecom.domain.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

enum class StoreApiStatus {LOADING, ERROR, DONE}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storeRepository: StoreRepository
) : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    private val _popularProducts = MutableStateFlow<List<Product>>(emptyList())
    val popularProducts = _popularProducts.asStateFlow()

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _status = MutableStateFlow<StoreApiStatus>(StoreApiStatus.LOADING)
    val status = _status.asStateFlow()

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            val result = storeRepository.getAllProducts()
            _products.value = result
        }

    }

}