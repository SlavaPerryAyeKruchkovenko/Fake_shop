package com.example.fake_shop.database.dao

import androidx.room.*
import com.example.fake_shop.database.entities.ProductEntity

@Dao
interface ProductDao {
    @Query("Select * from ProductEntity")
    suspend fun getProducts(): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE id = :id")
    suspend fun getProductById(id: String): ProductEntity?

    @Query("SELECT * FROM ProductEntity WHERE isLike = 1")
    suspend fun getLikedProducts(): List<ProductEntity>

    @Query("Delete from ProductEntity")
    suspend fun deleteProducts()

    @Query("UPDATE ProductEntity SET isLike = 0")
    suspend fun dislikeProducts()

    @Transaction
    suspend fun softInsertProducts(products: List<ProductEntity>) {
        val dbArtifacts = getProducts()
        products.forEach { product ->
            val dbArtifact = dbArtifacts.find { it.id == product.id }
            product.isLike = dbArtifact?.isLike ?: 0
            product.comment = dbArtifact?.comment
        }
        deleteProducts()
        insertArtifacts(products)
    }

    @Update
    suspend fun update(product: ProductEntity)

    @Insert
    suspend fun insertArtifacts(products: List<ProductEntity>)
}