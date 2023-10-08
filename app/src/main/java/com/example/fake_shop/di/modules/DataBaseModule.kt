package com.example.fake_shop.di.modules

import androidx.room.Room
import com.example.fake_shop.database.FakeShopDataBase
import org.koin.dsl.module

val dataBaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            FakeShopDataBase::class.java,
            "FakeShopDataBaseName"
        ).build()
    }
    single {
        get<FakeShopDataBase>().productsDao()
    }
}