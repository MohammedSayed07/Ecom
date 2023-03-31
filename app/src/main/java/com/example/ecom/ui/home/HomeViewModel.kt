package com.example.ecom.ui.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _categoryState = MutableLiveData<Int>()
    val categoryState: LiveData<Int> get() = _categoryState

    init {
        _categoryState.value = 0
        getCategories()
        getProducts("All")
    }

    fun getProducts(string: String) {
        viewModelScope.launch {
            _status.value = StoreApiStatus.LOADING

            val result: List<Product> = withContext(Dispatchers.IO) {
                if (string == "All") {
                    storeRepository.getAllProducts()
                } else {
                    storeRepository.getProductsOfCategory(string)
                }
            }

            _products.value = result
            _popularProducts.value = result.filter {
                it.rating.rate > 7.0
            }
            _status.value = StoreApiStatus.DONE
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            val result = storeRepository.getCategories()
            _categories.value = result
        }
    }

    fun setCategoryState(cate: Int) {
        _categoryState.value = cate
    }

}