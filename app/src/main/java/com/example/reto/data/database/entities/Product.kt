package com.example.reto.data.database.entities

import androidx.databinding.adapters.Converters
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable

@Entity(tableName = "products")
data class Product(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "description") val description: String,
    @TypeConverters(Converters::class)
    @ColumnInfo(name = "images") val images: List<String>,
    @ColumnInfo(name = "creationAt") val creationAt: String,
    @ColumnInfo(name = "updatedAt") val updatedAt: String,
    @ColumnInfo(name = "category_id") val categoryId: Int
): Serializable
