package com.example.fake_shop.di.modules

import com.example.fake_shop.networks.ProductApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val url = "https://fakestoreapi.com/"
val retrofitModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<ProductApi> { get<Retrofit>().create(ProductApi::class.java) }
}