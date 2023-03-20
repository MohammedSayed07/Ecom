package com.example.ecom.ui.productdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecom.data.Product

class ProductDetailsViewModel(product: Product): ViewModel() {

    private val _selectedProduct = MutableLiveData<Product>()
    val selectedProduct: LiveData<Product> get() = _selectedProduct

    init {
        _selectedProduct.value = product
    }



}