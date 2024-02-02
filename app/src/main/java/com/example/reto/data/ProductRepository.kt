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
import kotlinx.coroutines.flow.Flow
import org.json.JSONArray
import org.json.JSONException
import javax.inject.Inject


class ProductRepository @Inject constructor(
    private val productService: ApiService,
    private val productDao: ProductDao,
    private val cateriatDao: CategoryDao,
) {
    val products: Flow<List<Product>> = productDao.getAllProducts()

    suspend fun refreshProducts() {
        try {
            val response = productService.getAllProducts()
            if (response.isSuccessful) {
                response.body()?.let { productList ->
                    val cleanedProductList = productList.map { product ->
                        product.copy(
                            images = cleanAndParseImageUrls(product.images.joinToString(","))
                        )
                    }

                    val productEntities = cleanedProductList.map { it.toProductEntity() }
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
                Log.e("ProductRepository", "Error al llamar al servicio")
            }
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error implementación", e)
        }
    }

    fun searchProducts(searchQuery: String): Flow<List<Product>> {
        return productDao.searchProducts("%$searchQuery%")
    }

    fun cleanAndParseImageUrls(imageData: String): List<String> {
        val urls = if (imageData.trim().startsWith("[")) {
            try {
                JSONArray(imageData).let { jsonArray ->
                    (0 until jsonArray.length()).map { index -> jsonArray.getString(index) }
                }
            } catch (e: JSONException) {
                emptyList<String>()
            }
        } else {
            imageData.split(",").map { it.trim() }
        }
        return urls.map { it.replace("\\", "") }.also { cleanedUrls ->
            cleanedUrls.forEach { url ->
                Log.d("ProductRepository", "Muestra de  $url")
            }
        }
    }

}