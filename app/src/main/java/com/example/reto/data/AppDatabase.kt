package com.example.reto.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.reto.data.database.dao.CategoryDao
import com.example.reto.data.database.dao.ProductDao
import com.example.reto.data.database.entities.Category
import com.example.reto.data.database.entities.Converters
import com.example.reto.data.database.entities.Product

@Database(entities = [Product::class, Category::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao

}