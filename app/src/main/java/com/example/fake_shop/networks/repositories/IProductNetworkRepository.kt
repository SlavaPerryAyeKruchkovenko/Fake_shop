package com.example.fake_shop.networks.repositories

import com.example.fake_shop.data.responses.ProductResponse
import retrofit2.Response

interface IProductNetworkRepository {
    suspend fun getProducts(): Response<Array<ProductResponse>>
    suspend fun getProduct(id: String): Response<ProductResponse>
}