package com.example.reto.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.reto.data.database.dao.CategoryDao
import com.example.reto.data.database.dao.ProductDao
import com.example.reto.data.database.data.toCategoryEntity
import com.example.reto.data.database.data.toProductEntity
import com.example.reto.data.database.entities.Category
import com.example.reto.data.database.entities.Product
import com.example.reto.network.ApiService
import javax.inject.Inject


class ProductRepository @Inject constructor(
    private val productService: ApiService,
    private val productDao: ProductDao,
    private val cateriatDao: CategoryDao,
) {
    val products: LiveData<List<Product>> = productDao.getAllProducts()
    val category: LiveData<List<Category>> = cateriatDao.getAllCategories()

    suspend fun refreshProducts() {
        try {
            val response = productService.getAllProducts()
            if (response.isSuccessful) {
                response.body()?.let { productList ->
                    val productEntities = productList.map { it.toProductEntity() }
                    productDao.insertAll(productEntities)
                    val categoryEntities = productList.map { it.category.toCategoryEntity() }
                    cateriatDao.insertCategory(categoryEntities)
                    productList.map { product ->
                        product.copy(
                            images = product.images.map { it.replace("\\", "") }
                        )
                    }
                }
            } else {
                Log.e("ProductRepository", "Error fetching products: ${response.errorBody()}")
            }
        } catch (e: Exception) {
            Log.e("ProductRepository", "Exception fetching products", e)
        }
    }
}