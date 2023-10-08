package com.example.fake_shop.domain.useCases

import com.example.fake_shop.domain.interfaces.IDislikeProductsUseCase
import com.example.fake_shop.repository.interfaces.IProductRepository

class DislikeProductsUseCase(private val repository: IProductRepository): IDislikeProductsUseCase {
    override suspend fun invoke(): Boolean {
        TODO("Not yet implemented")
    }
}