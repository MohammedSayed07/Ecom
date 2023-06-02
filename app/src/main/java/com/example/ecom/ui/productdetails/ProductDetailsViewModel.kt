package com.example.ecom.ui.productdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecom.data.local.entities.CartEntity
import com.example.ecom.data.local.entities.WishItemEntity
import com.example.ecom.domain.StoreRepository
import com.example.ecom.domain.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val storeRepository: StoreRepository
): ViewModel() {

    private val _selectedProduct = MutableLiveData<Product>()
    val selectedProduct: LiveData<Product> get() = _selectedProduct

    private val _addedToFavoriteChannel = Channel<Unit>()
    val addedToFavorite = _addedToFavoriteChannel.receiveAsFlow()

    fun setProduct(product: Product){
        _selectedProduct.value = product
    }

    suspend fun addItemToCart(userId: Int) {
        coroutineScope {
            val productId = selectedProduct.value!!.id

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
        }

    }

    suspend fun addItemToWishlist(userId: Int) {
        viewModelScope.launch {
            val productId = selectedProduct.value!!.id
            var isAdded = false

            val wishItems = storeRepository.getUserWithWishItems(userId)[0].wishItems
            for (i in wishItems) {
                if (i.pid == productId) {
                    isAdded = true
                    break
                }
            }
            if (!isAdded) {
                storeRepository.insertWishItem(WishItemEntity(productId, userId))
                _addedToFavoriteChannel.send(Unit)
            }
        }
    }
}