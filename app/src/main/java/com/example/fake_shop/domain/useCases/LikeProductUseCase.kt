package com.example.fake_shop.domain.useCases

import com.example.fake_shop.data.converters.ProductConverter
import com.example.fake_shop.domain.interfaces.ILikeProductUseCase
import com.example.fake_shop.repository.interfaces.IProductRepository

class LikeProductUseCase(private val repository: IProductRepository) : ILikeProductUseCase {
    override suspend fun invoke(product: ProductConverter): ProductConverter? {
        product.isLike = true
        return repository.updateProduct(product)
    }
}