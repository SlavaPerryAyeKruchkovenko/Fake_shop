package com.example.fake_shop.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val count: Int,
    val rating: Double,
    val image: String,
    var isLike: Int,
    var comment: String?
)