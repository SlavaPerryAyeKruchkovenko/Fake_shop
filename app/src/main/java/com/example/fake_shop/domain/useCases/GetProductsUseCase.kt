package com.example.fake_shop.domain.useCases

import com.example.fake_shop.data.converters.ProductConverter
import com.example.fake_shop.data.models.OutputOf
import com.example.fake_shop.domain.interfaces.IGetProductsUseCase
import com.example.fake_shop.repository.interfaces.IProductRepository

class GetProductsUseCase(private val repository: IProductRepository) : IGetProductsUseCase {
    override suspend fun invoke(): OutputOf<List<ProductConverter>> {
        return repository.getProducts()
    }
}