package com.example.fake_shop.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fake_shop.database.dao.ProductDao
import com.example.fake_shop.database.entities.ProductEntity

@Database(
    entities = [ProductEntity::class], version = 1
)
abstract class FakeShopDataBase : RoomDatabase() {
    abstract fun productsDao(): ProductDao
}