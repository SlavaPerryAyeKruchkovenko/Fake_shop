package com.example.fake_shop.domain.interfaces

import com.example.fake_shop.data.converters.ProductConverter

interface ILikeProductUseCase {
    suspend operator fun invoke(product: ProductConverter): ProductConverter?
}