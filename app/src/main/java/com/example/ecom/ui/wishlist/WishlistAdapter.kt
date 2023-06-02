package com.example.ecom.ui.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ecom.R
import com.example.ecom.databinding.WishItemBinding
import com.example.ecom.domain.models.WishItem

class WishlistAdapter(
    val addToCart: (Int) -> Unit
): ListAdapter<WishItem, WishlistAdapter.ViewHolder>(DiffCallback) {
    class ViewHolder(private val binding: WishItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            wishItem: WishItem,
            addToCart: (Int) -> Unit
        ) {
            Glide.with(binding.productImage.context)
                .load(wishItem.image.toUri())
                .apply(RequestOptions().placeholder(R.drawable.loading_animation))
                .into(binding.productImage)

            binding.title.text = wishItem.title
            binding.price.text = wishItem.price.toString()

            binding.addToCart.setOnClickListener {
                addToCart(wishItem.pid)
            }
        }
    }

    private object DiffCallback: DiffUtil.ItemCallback<WishItem>() {
        override fun areItemsTheSame(oldItem: WishItem, newItem: WishItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: WishItem, newItem: WishItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(WishItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wishItem = getItem(position)
        holder.bind(wishItem, addToCart)
    }
}