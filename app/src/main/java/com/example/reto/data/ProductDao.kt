package com.example.reto.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Query("SELECT * FROM products ORDER BY price ASC")
    fun getAllProducts():LiveData<List<Product>>

    @Query("SELECT * FROM products WHERE name LIKE :name ORDER BY price ASC")
    fun findProductByName(name: String): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg products: Product)

    @Delete
    suspend fun delete(product: Product)
}