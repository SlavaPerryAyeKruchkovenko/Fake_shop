package com.example.fake_shop.repository.interfaces

import com.example.fake_shop.data.converters.ProductConverter
import com.example.fake_shop.data.models.OutputOf

interface IProductRepository {
    suspend fun getProducts(): OutputOf<List<ProductConverter>>
    suspend fun getProductById(id: String): OutputOf<ProductConverter?>
    suspend fun updateProduct(product: ProductConverter): ProductConverter
    suspend fun getLikedProducts(): List<ProductConverter>
    suspend fun dislikeProducts(): Boolean
}