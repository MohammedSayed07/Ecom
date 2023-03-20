package com.example.ecom.ui.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecom.databinding.HomeCategoryItemBinding

class CategoryAdapter: ListAdapter<String, CategoryAdapter.ViewHolder>(DiffCallback) {
    var selectedPosition = 0
    var selectedCategory: ((String) -> Unit)? = null
    inner class ViewHolder(private val binding: HomeCategoryItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(
            category: String,
            selectedCategory: ((String) -> Unit)?,
            position: Int
        ) {
            binding.category.text = category
            if (selectedPosition == position) {
                binding.card.setCardBackgroundColor(Color.parseColor("#000000"))
                binding.category.setTextColor(Color.parseColor("#FFFFFF"))
            } else {
                binding.card.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.category.setTextColor(Color.parseColor("#000000"))
            }
            itemView.setOnClickListener {
                selectedCategory?.invoke(category)
                selectedPosition = position
                notifyDataSetChanged()
            }
        }
    }

    object DiffCallback: DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(HomeCategoryItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category, selectedCategory, position)
    }

}