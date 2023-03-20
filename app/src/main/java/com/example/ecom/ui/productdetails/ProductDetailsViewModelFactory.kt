package com.example.ecom.ui.productdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ecom.data.Product

class ProductDetailsViewModelFactory(
    private val selectedProduct: Product
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)) {
            return ProductDetailsViewModel(selectedProduct) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel Class")
    }
}