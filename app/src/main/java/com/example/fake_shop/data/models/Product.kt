package com.example.fake_shop.data.models

data class Product(
    val id: String,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val count: Int,
    val rating: Double,
    val isLike: Boolean
)
