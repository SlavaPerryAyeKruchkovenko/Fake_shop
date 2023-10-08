package com.example.fake_shop.domain.useCases

import com.example.fake_shop.data.converters.ProductConverter
import com.example.fake_shop.data.models.OutputOf
import com.example.fake_shop.domain.interfaces.IGetProductUseCase
import com.example.fake_shop.repository.interfaces.IProductRepository

class GetProductUseCase(private val repository: IProductRepository) : IGetProductUseCase {
    override suspend fun invoke(productId: String): OutputOf<ProductConverter?> {
        return repository.getProductById(productId)
    }
}