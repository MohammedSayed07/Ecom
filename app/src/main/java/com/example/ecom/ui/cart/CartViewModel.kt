package com.example.ecom.ui.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecom.data.local.CartEntity
import com.example.ecom.data.local.Database
import com.example.ecom.domain.StoreRepository
import com.example.ecom.domain.models.CartItem
import com.example.ecom.ui.home.StoreApiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

enum class CartStatus {LOADING, ERROR, DONE}
@HiltViewModel
class CartViewModel @Inject constructor(
    private val storeRepository: StoreRepository,
    private val db: Database
) : ViewModel() {
    private val _products = MutableStateFlow<List<CartItem>?>(emptyList())
    val products = _products.asStateFlow()

    private val _totalPrice = MutableStateFlow<Double?>(0.0)
    val totalPrice = _totalPrice.asStateFlow()

    private val _status = MutableStateFlow<CartStatus>(CartStatus.DONE)
    val status = _status.asStateFlow()

    init {
        getCartProducts()
    }

    private fun getCartProducts() {
        viewModelScope.launch {
            storeRepository.getAllCartEntitiesFlow().shareIn(viewModelScope, started = SharingStarted.WhileSubscribed(), replay = 1).collect{
                var total: Double? = 0.0
                try {
                    if (total != null) {
                        _products.value = it.map {
                            _status.value = CartStatus.LOADING
                            val product = storeRepository.getProduct(it.pid)
                            if (product != null) {
                                total += (product.price.times(it.quantity))
                            }
                            CartItem(product!!, it.quantity)
                        }
                    }
                    _totalPrice.value = total
                } catch (e: Exception) {
                    Log.e("Exception", e.toString())
                }
                _totalPrice.value = total
                _status.value = CartStatus.DONE
                }
        }
    }

    fun decreaseQuantity(id: Int) {
        viewModelScope.launch {
            val cartEntity = storeRepository.getCartEntity(id)
            val quantity = cartEntity.quantity - 1
            if (quantity > 0)
                storeRepository.updateCart(CartEntity(pid = id, quantity = quantity))
            else
                storeRepository.deleteCart(cartEntity)
        }
    }

    fun increaseQuantity(id: Int) {
        viewModelScope.launch {
            val quantity = storeRepository.getCartEntity(id).quantity

            storeRepository.updateCart(CartEntity(pid = id, quantity = quantity + 1))
        }
    }

}