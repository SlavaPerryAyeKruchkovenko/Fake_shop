package com.example.fake_shop.domain.useCases

import com.example.fake_shop.data.converters.ProductConverter
import com.example.fake_shop.domain.interfaces.IGetLikedProductsUseCase
import com.example.fake_shop.repository.interfaces.IProductRepository

class GetLikedProductsUseCase(private val repository: IProductRepository) :
    IGetLikedProductsUseCase {
    override suspend fun invoke(): List<ProductConverter> {
        return repository.getLikedProducts()
    }
}