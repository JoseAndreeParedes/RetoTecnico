package com.example.reto.data.database.data

import com.example.reto.data.database.entities.Product

data class Productos(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val images: List<String>,
    val creationAt: String,
    val updatedAt: String,
    val category: Categoria
)

fun Productos.toProductEntity(): Product {
    return Product(
        id = this.id,
        title = this.title,
        price = this.price,
        description = this.description,
        images = this.images,
        creationAt = this.creationAt,
        updatedAt = this.updatedAt,
        categoryId = this.category.id
    )
}