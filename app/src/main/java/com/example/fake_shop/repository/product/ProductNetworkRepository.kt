package com.example.fake_shop.repository.product

import com.example.fake_shop.data.responses.ProductResponse
import com.example.fake_shop.networks.ProductApi
import com.example.fake_shop.networks.repositories.IProductNetworkRepository
import retrofit2.Response

class ProductNetworkRepository(private val api: ProductApi) : IProductNetworkRepository {
    override suspend fun getProducts(): Response<Array<ProductResponse>> {
        return api.getProducts()
    }

    override suspend fun getProduct(id: String): Response<ProductResponse> {
        return api.getProduct(id)
    }
}