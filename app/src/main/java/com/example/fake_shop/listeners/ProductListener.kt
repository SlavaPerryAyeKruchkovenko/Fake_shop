package com.example.fake_shop.listeners

import com.example.fake_shop.data.models.Product

interface ProductListener {
    fun onClick(product: Product)
}