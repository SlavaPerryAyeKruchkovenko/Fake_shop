package com.example.fake_shop.database.repositories

import com.example.fake_shop.database.entities.ProductEntity

interface IProductLocalRepository {
    suspend fun addProducts(artifacts: List<ProductEntity>)
    suspend fun updateProduct(product: ProductEntity)
    suspend fun getProducts(): List<ProductEntity>?
    suspend fun getLikedProducts(): List<ProductEntity>?
    suspend fun getProduct(id: String): ProductEntity?
    suspend fun dislikeProducts()
}