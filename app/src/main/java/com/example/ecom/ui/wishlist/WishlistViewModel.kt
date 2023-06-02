package com.example.ecom.ui.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecom.data.local.entities.CartEntity
import com.example.ecom.data.local.entities.WishItemEntity
import com.example.ecom.domain.StoreRepository
import com.example.ecom.domain.models.WishItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val storeRepository: StoreRepository
): ViewModel() {
    private val _products = MutableStateFlow<List<WishItem?>?>(emptyList())
    val products = _products.asStateFlow()


    fun getCartProducts(userId: Int) {
        viewModelScope.launch {
            storeRepository.getUserWithWishItemsFlow(userId).shareIn(viewModelScope, started = SharingStarted.WhileSubscribed(), replay = 1).collectLatest{ it ->
                _products.value = it.wishItems.map {    wishItemEntity ->
                    val product = storeRepository.getProduct(wishItemEntity.pid)
                    product?.let {
                        WishItem(it.id, it.title, it.price, it.image)
                    }
                }
            }
        }
    }
     fun addItemToCart(userId: Int, productId: Int) {
        viewModelScope.launch {
            val carts = storeRepository.getAllCartEntities(userId)[0].carts

            var bool = false
            for(i in carts){
                if (i.pid == productId) {
                    storeRepository.updateCart(CartEntity(i.pid, quantity = i.quantity + 1, userId))
                    bool = true
                    break
                }
            }
            if (!bool) {
                storeRepository.insertCart(CartEntity(productId, 1, userId))
            }

            storeRepository.deleteWishItem(WishItemEntity(productId, userId))

        }

    }
}