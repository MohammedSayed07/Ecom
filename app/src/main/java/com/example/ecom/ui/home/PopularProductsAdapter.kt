package com.example.ecom.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecom.databinding.ProductItemBinding
import com.example.ecom.domain.models.Product

class PopularProductsAdapter(
    val productClicked: (Product) -> Unit
): ListAdapter<Product, PopularProductsAdapter.ProductsViewHolder>(DiffCallback) {

    class ProductsViewHolder private constructor(private var binding: ProductItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, productClicked: (Product) -> Unit) {
            binding.product = product
            binding.executePendingBindings()

            itemView.setOnClickListener {
                productClicked(product)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ProductsViewHolder {
                return ProductsViewHolder(ProductItemBinding.inflate(LayoutInflater.from(parent.context)))
            }
        }
    }

    private object DiffCallback: DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product, productClicked)


    }


}