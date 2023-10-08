package com.example.fake_shop.di.modules

import com.example.fake_shop.ui.product.ProductViewModel
import com.example.fake_shop.ui.shop.ShopViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { ShopViewModel(get()) }
    viewModel { ProductViewModel(get()) }
}