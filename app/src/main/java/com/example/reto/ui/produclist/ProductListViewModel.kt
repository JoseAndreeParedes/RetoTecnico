package com.example.reto.ui.produclist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reto.data.ProductRepository
import com.example.reto.data.database.entities.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    val products: Flow<List<Product>> = repository.products

    enum class SortOrder { ASCENDING, DESCENDING }

    private val _currentSortOrder = MutableStateFlow<SortOrder>(SortOrder.DESCENDING)
    val currentSortOrder: StateFlow<SortOrder> = _currentSortOrder

    private var currentQuery = ""

    private val _filteredProducts = MutableStateFlow<List<Product>>(emptyList())
    val filteredProducts: StateFlow<List<Product>> = _filteredProducts.asStateFlow()

    private val _shouldScrollToTop = MutableStateFlow<Boolean>(false)
    val shouldScrollToTop: StateFlow<Boolean> = _shouldScrollToTop

    init {
        loadProducts()
        observeProducts()
        sortProducts(SortOrder.DESCENDING)
    }

    fun sortProducts(order: SortOrder) {
        _currentSortOrder.value = order
        val sortedList = when (order) {
            SortOrder.ASCENDING -> _filteredProducts.value?.sortedBy { it.price }
            SortOrder.DESCENDING -> _filteredProducts.value?.sortedByDescending { it.price }
        }
        _filteredProducts.value = sortedList
    }


    private fun loadProducts() {
        viewModelScope.launch {
            repository.refreshProducts()
        }
    }

    private fun observeProducts() {
        viewModelScope.launch {
            repository.products.collect { products ->
                filterProducts(currentQuery)
            }
        }
    }

    fun filterProducts(query: String) {
        currentQuery = query
        viewModelScope.launch {
            if (query.isEmpty()) {
                repository.products.collect { products ->
                    _filteredProducts.value = products
                }
            } else {
                repository.searchProducts(query).collect { searchedProducts ->
                    _filteredProducts.value = searchedProducts
                }
            }
        }
    }

    fun sortProductsAscending() {
        val sortedList = _filteredProducts.value?.sortedBy { it.price }
        _filteredProducts.value = sortedList!!
        _shouldScrollToTop.value = true
    }

    fun sortProductsDescending() {
        val sortedList = _filteredProducts.value?.sortedByDescending { it.price }
        _filteredProducts.value = sortedList!!
        _shouldScrollToTop.value = true
    }

    fun onScrollToTopHandled() {
        _shouldScrollToTop.value = false
    }

}