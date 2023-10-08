package com.example.fake_shop.di.modules.data_modules

import com.example.fake_shop.networks.repositories.IProductNetworkRepository
import com.example.fake_shop.repository.product.ProductNetworkRepository
import org.koin.dsl.module

val networkDataModule = module {
    single<IProductNetworkRepository> {
        ProductNetworkRepository(api = get())
    }
}