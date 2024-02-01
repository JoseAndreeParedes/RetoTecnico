package com.example.reto.ui.produclist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reto.data.ProductRepository
import com.example.reto.data.database.entities.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    val products: LiveData<List<Product>> = repository.products

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            repository.refreshProducts()
        }
    }
}