package com.example.reto.data.database.data

import com.example.reto.data.database.entities.Category

data class Categoria(
    val id: Int,
    val name: String,
    val image: String,
    val creationAt: String,
    val updatedAt: String
)

fun Categoria.toCategoryEntity(): Category {
    return Category(
        id = this.id,
        name = this.name,
        image = this.image,
        creationAt = this.creationAt,
        updatedAt = this.updatedAt
    )
}
