package com.example.fake_shop.di.modules.data_modules

import com.example.fake_shop.database.repositories.IProductLocalRepository
import com.example.fake_shop.repository.product.ProductLocalRepository
import org.koin.dsl.module

val localDataModule = module {
    single<IProductLocalRepository> {
        ProductLocalRepository(dao = get())
    }
}