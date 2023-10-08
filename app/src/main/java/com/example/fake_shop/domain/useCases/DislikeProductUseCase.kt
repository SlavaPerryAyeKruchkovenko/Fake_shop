package com.example.fake_shop.domain.useCases

import com.example.fake_shop.data.converters.ProductConverter
import com.example.fake_shop.domain.interfaces.IDislikeProductUseCase
import com.example.fake_shop.repository.interfaces.IProductRepository

class DislikeProductUseCase(private val repository: IProductRepository) : IDislikeProductUseCase {
    override suspend fun invoke(product: ProductConverter): ProductConverter? {
        product.isLike = false
        return repository.updateProduct(product)

    }
}