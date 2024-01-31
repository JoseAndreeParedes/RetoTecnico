package com.example.reto.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.reto.data.ProductRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    val products = liveData(Dispatchers.IO) {
        val retrievedProducts = repository.getAllProducts()
        emit(retrievedProducts.body() ?: emptyList())
    }
}