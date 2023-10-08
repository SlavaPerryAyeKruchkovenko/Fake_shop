package com.example.fake_shop.domain.interfaces

import com.example.fake_shop.data.converters.ProductConverter

interface IDislikeProductUseCase {
    suspend operator fun invoke(product: ProductConverter): ProductConverter?
}