package com.example.fake_shop.networks

import com.example.fake_shop.data.responses.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {
    @GET("products")
    suspend fun getProducts(): Response<Array<ProductResponse>>

    @GET("products/{product}")
    suspend fun getProduct(@Path("product") productId: String): Response<ProductResponse>
}