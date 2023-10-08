package com.example.fake_shop.domain.interfaces

import com.example.fake_shop.data.converters.ProductConverter

interface IGetLikedProductsUseCase {
    suspend operator fun invoke(): List<ProductConverter>
}