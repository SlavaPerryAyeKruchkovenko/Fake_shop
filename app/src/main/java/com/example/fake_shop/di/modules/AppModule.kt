package com.example.fake_shop.di.modules

import com.example.fake_shop.di.modules.data_modules.localDataModule
import com.example.fake_shop.di.modules.data_modules.networkDataModule
import com.example.fake_shop.di.modules.data_modules.unitedDataModule
import com.example.fake_shop.di.modules.domain_modules.productDomainModule
import org.koin.core.module.Module

val appModules: List<Module> = listOf(
    retrofitModule,
    localDataModule,
    networkDataModule,
    unitedDataModule,
    productDomainModule,
    viewModelsModule
)