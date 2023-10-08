package com.example.fake_shop.repository.product

import com.example.fake_shop.data.converters.ProductConverter
import com.example.fake_shop.data.models.OutputOf
import com.example.fake_shop.networks.repositories.IProductNetworkRepository
import com.example.fake_shop.repository.interfaces.IProductRepository
import java.net.UnknownHostException

class ProductRepository(
    private val network: IProductNetworkRepository
) :IProductRepository {
    override suspend fun getProducts(): OutputOf<List<ProductConverter>> {
        return try {
            val res = network.getProducts()
            if (res.isSuccessful) {
                val productsResponse = res.body()!!
                val products = productsResponse.map {
                    ProductConverter.fromArtifactResponse(it)
                }
                /*val productsEntity = products.map {
                    it.toArtifactEntity()
                }
                local.addArtifacts(artifactsEntity)*/
                OutputOf.Success(products)
            } else {
                OutputOf.Error.ResponseError(listOf())
            }
        } catch (e: UnknownHostException) {
            OutputOf.Error.InternetError(listOf())
            /*local.getArtifacts()?.map {
                ArtifactConvert.fromArtifactEntity(it)
            } ?: listOf()*/
        }
    }

    override suspend fun getProductById(id: String): OutputOf<ProductConverter?> {
        return try {
            val res = network.getProduct(id)
            if (res.isSuccessful) {
                val productResponse = res.body()!!
                val product = ProductConverter.fromArtifactResponse(productResponse)
                OutputOf.Success(product)
            } else {
                OutputOf.Error.ResponseError(null)
            }
        } catch (e: UnknownHostException) {
            OutputOf.Error.InternetError(null)
        }
    }

    override suspend fun updateProduct(product: ProductConverter): ProductConverter {
        TODO("Not yet implemented")
    }

    override suspend fun getLikedProducts(): List<ProductConverter> {
        TODO("Not yet implemented")
    }

    override suspend fun dislikeProducts(): Boolean {
        TODO("Not yet implemented")
    }
}