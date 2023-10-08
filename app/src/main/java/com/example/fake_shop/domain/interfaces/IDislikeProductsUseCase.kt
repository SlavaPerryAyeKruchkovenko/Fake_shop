package com.example.fake_shop.domain.interfaces

interface IDislikeProductsUseCase {
    suspend operator fun invoke(): Boolean
}