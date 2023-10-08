package com.example.fake_shop.di.modules.data_modules

import com.example.fake_shop.repository.interfaces.IProductRepository
import com.example.fake_shop.repository.product.ProductRepository
import org.koin.dsl.module

val unitedDataModule = module {
    single<IProductRepository> {
        ProductRepository(local = get(), network = get())
    }
}