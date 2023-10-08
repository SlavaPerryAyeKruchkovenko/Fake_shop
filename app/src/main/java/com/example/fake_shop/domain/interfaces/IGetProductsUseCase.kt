package com.example.fake_shop.domain.interfaces

import com.example.fake_shop.data.converters.ProductConverter
import com.example.fake_shop.data.models.OutputOf

interface IGetProductsUseCase {
    suspend operator fun invoke(): OutputOf<List<ProductConverter>>
}