package com.example.ecom.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ecom.R
import com.example.ecom.bindStatus
import com.example.ecom.databinding.CartItemBinding
import com.example.ecom.domain.models.CartItem

class CartAdapter(
    val decreaseClicked: (Int) -> Unit,
    val increaseClicked: (Int) -> Unit
): ListAdapter<CartItem, CartAdapter.CartViewHolder>(DiffCallback) {



    class CartViewHolder(private val binding: CartItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            cartItem: CartItem,
            decreaseClicked: ((Int) -> Unit),
            increaseClicked: ((Int) -> Unit)
        ) {
            Glide.with(binding.productImage.context)
                .load(cartItem.product.image.toUri())
                .apply(RequestOptions().placeholder(R.drawable.loading_animation))
                .into(binding.productImage)

            binding.productTitle.text = cartItem.product.title
            binding.productQuantity.text = cartItem.quantity.toString()
            binding.productPrice.text = String.format("%.2f" ,cartItem.product.price * cartItem.quantity)

            binding.decrease.setOnClickListener {
                decreaseClicked(cartItem.product.id)
            }

            binding.increase.setOnClickListener {
                increaseClicked(cartItem.product.id)
            }

        }
    }

    private object DiffCallback: DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.product.id == newItem.product.id
        }

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(CartItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = getItem(position)
        holder.bind(cartItem, decreaseClicked, increaseClicked)
    }
}