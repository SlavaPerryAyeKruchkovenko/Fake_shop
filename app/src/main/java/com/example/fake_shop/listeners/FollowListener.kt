package com.example.fake_shop.listeners

import com.example.fake_shop.data.models.Product

interface FollowListener {
    fun onClick(product: Product)
}