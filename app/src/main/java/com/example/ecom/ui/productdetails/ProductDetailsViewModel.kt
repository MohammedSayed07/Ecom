package com.example.ecom.ui.productdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecom.data.local.CartEntity
import com.example.ecom.domain.StoreRepository
import com.example.ecom.domain.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val storeRepository: StoreRepository
): ViewModel() {

    private val _selectedProduct = MutableLiveData<Product>()
    val selectedProduct: LiveData<Product> get() = _selectedProduct

    fun setProduct(product: Product){
        _selectedProduct.value = product
    }

    suspend fun addItemToCart() {
        coroutineScope {
            val productId = selectedProduct.value!!.id

            val carts = storeRepository.getAllCartEntities()

            var bool = false
            for(i in carts){
                if (i.pid == productId) {
                    storeRepository.updateCart(CartEntity(i.pid, quantity = i.quantity + 1))
                    bool = true
                    break
                }
            }
            if (!bool) {
                storeRepository.insertCart(CartEntity(productId, 1))
            }
        }

    }
}