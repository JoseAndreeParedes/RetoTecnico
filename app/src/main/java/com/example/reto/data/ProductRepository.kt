package com.example.reto.data

import com.example.reto.network.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getAllProducts() = apiService.getAllProducts()
}