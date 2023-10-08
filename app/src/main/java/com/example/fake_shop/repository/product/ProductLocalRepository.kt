package com.example.fake_shop.repository.product

import com.example.fake_shop.database.dao.ProductDao
import com.example.fake_shop.database.entities.ProductEntity
import com.example.fake_shop.database.repositories.IProductLocalRepository

class ProductLocalRepository(private val dao: ProductDao) : IProductLocalRepository {
    override suspend fun addProducts(products: List<ProductEntity>) {
        dao.softInsertProducts(products)
    }

    override suspend fun updateProduct(product: ProductEntity) {
        dao.update(product)
    }

    override suspend fun getProducts(): List<ProductEntity>? {
        return dao.getProducts()
    }

    override suspend fun getLikedProducts(): List<ProductEntity>? {
        return dao.getLikedProducts()
    }

    override suspend fun getProduct(id: String): ProductEntity? {
        return dao.getProductById(id)
    }

    override suspend fun dislikeProducts() {
        dao.dislikeProducts()
    }
}