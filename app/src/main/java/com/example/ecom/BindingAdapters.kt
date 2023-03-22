package com.example.ecom

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ecom.data.Product
import com.example.ecom.ui.home.SaleAdapter
import com.example.ecom.ui.home.PopularProductsAdapter
import com.example.ecom.ui.home.StoreApiStatus

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        var imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions().placeholder(R.drawable.loading_animation))
            .into(imgView)
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Product>?) {
    val adapter = recyclerView.adapter as PopularProductsAdapter
    adapter.submitList(data)
}

@BindingAdapter("saleData")
fun bindSaleData(recyclerView: RecyclerView, data: List<Product>?) {
    val adapter = recyclerView.adapter as SaleAdapter
    adapter.submitList(data)
}

@BindingAdapter("storeApiStatus")
fun bindStatus(statusImageView: ImageView, status: StoreApiStatus?) {
    when (status) {
        StoreApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        StoreApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        StoreApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        else -> {}
    }
}