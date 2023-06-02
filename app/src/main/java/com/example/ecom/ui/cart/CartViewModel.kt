package com.example.ecom.ui.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecom.data.local.entities.CartEntity
import com.example.ecom.data.local.Database
import com.example.ecom.domain.StoreRepository
import com.example.ecom.domain.models.CartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
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

    private val _userId = MutableStateFlow(0)

    private var _isBackButtonPressed = false

    private val confirmPopBackStackChannel = Channel<Unit>()
    val confirmPopBackStack = confirmPopBackStackChannel.receiveAsFlow()

    private val closeAppChannel = Channel<Unit>()
    val closeApp = closeAppChannel.receiveAsFlow()

    fun getCartProducts(userId: Int) {
        viewModelScope.launch {
            storeRepository.getUserWithCartsFlow(userId).shareIn(viewModelScope, started = SharingStarted.WhileSubscribed(), replay = 1).collect{
                var total: Double? = 0.0
                try {
                    if (total != null) {
                        _products.value = it.carts.map {
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

    fun decreaseQuantity(id: Int, userId: Int) {
        viewModelScope.launch {
            val cartEntity = storeRepository.getCartEntity(id)
            if ((cartEntity.quantity - 1) > 0)
                storeRepository.updateCart(CartEntity(pid = id, quantity = cartEntity.quantity - 1, userId))
            else
                storeRepository.deleteCart(cartEntity)
        }
    }

    fun increaseQuantity(id: Int, userId: Int) {
        viewModelScope.launch {
            val quantity = storeRepository.getCartEntity(id).quantity

            storeRepository.updateCart(CartEntity(pid = id, quantity = quantity + 1, userId))
        }
    }

     fun backButtonPressed() {
         viewModelScope.launch {
             if (_isBackButtonPressed) {
                 closeAppChannel.send(Unit)
             } else {
                 confirmPopBackStackChannel.send(Unit)
             }

             _isBackButtonPressed = true
             var count = 4
             while (count != 0) {
                 delay(1000L)
                 count--
             }
             _isBackButtonPressed = false
         }
    }

}