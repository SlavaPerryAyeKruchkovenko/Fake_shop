package com.example.fake_shop.di.modules.domain_modules

import com.example.fake_shop.domain.interfaces.*
import com.example.fake_shop.domain.useCases.*
import org.koin.dsl.module

val productDomainModule = module {
    factory<IGetProductsUseCase> {
        GetProductsUseCase(repository = get())
    }
    factory<IGetProductUseCase> {
        GetProductUseCase(repository = get())
    }
    factory<IGetLikedProductsUseCase> {
        GetLikedProductsUseCase(repository = get())
    }
    factory<ILikeProductUseCase> {
        LikeProductUseCase(repository = get())
    }
    factory<IDislikeProductUseCase> {
        DislikeProductUseCase(repository = get())
    }
    factory<IDislikeProductsUseCase> {
        DislikeProductsUseCase(repository = get())
    }
}