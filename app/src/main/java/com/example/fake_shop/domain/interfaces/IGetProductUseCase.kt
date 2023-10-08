package com.example.fake_shop.domain.interfaces

import com.example.fake_shop.data.converters.ProductConverter
import com.example.fake_shop.data.models.OutputOf

interface IGetProductUseCase {
    suspend operator fun invoke(productId: String): OutputOf<ProductConverter?>
}