package com.example.reto.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.reto.data.database.entities.Product
import com.example.reto.databinding.RowProductBinding
import com.squareup.picasso.Picasso

class ProductAdapter(private val onProductClickListener: OnProductClickListener) :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(DiffCallback) {

    interface OnProductClickListener {
        fun onProductClicked(product: Product)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = RowProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, onProductClickListener)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = getItem(position)
        holder.bind(currentProduct)
    }

    class ProductViewHolder(
        private val binding: RowProductBinding,
        private val onProductClickListener: OnProductClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                binding.executePendingBindings()
                lblProductName.text = product.title
                lblPrice.text = "$${product.price}"
                if (!product.images.isNullOrEmpty()) {
                    Picasso.get().load(product.images[0]).into(imgProduct)
                }
                imgProduct.setOnClickListener {
                    onProductClickListener.onProductClicked(product)
                }
                imgProduct.setOnClickListener {
                    onProductClickListener.onProductClicked(product)
                }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }
}