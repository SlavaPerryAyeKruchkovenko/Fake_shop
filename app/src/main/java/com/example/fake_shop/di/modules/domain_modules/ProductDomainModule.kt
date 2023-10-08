package com.example.fake_shop.di.modules.domain_modules

import com.example.fake_shop.domain.interfaces.IGetProductsUseCase
import com.example.fake_shop.domain.useCases.GetProductsUseCase
import org.koin.dsl.module

val productDomainModule = module {
    factory<IGetProductsUseCase> {
        GetProductsUseCase(repository = get())
    }
}