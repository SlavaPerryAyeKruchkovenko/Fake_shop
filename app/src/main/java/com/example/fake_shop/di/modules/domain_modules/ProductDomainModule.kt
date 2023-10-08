package com.example.fake_shop.di.modules.domain_modules

import com.example.fake_shop.domain.interfaces.IGetProductUseCase
import com.example.fake_shop.domain.interfaces.IGetProductsUseCase
import com.example.fake_shop.domain.useCases.GetProductUseCase
import com.example.fake_shop.domain.interfaces.useCases.GetProductsUseCase
import org.koin.dsl.module

val productDomainModule = module {
    factory<IGetProductsUseCase> {
        GetProductsUseCase(repository = get())
    }
    factory<IGetProductUseCase> {
        GetProductUseCase(repository = get())
    }
}