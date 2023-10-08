package com.example.fake_shop.repository.product

import android.util.Log
import com.example.fake_shop.data.converters.ProductConverter
import com.example.fake_shop.data.models.OutputOf
import com.example.fake_shop.database.repositories.IProductLocalRepository
import com.example.fake_shop.networks.repositories.IProductNetworkRepository
import com.example.fake_shop.repository.interfaces.IProductRepository
import java.net.UnknownHostException

class ProductRepository(
    private val local: IProductLocalRepository,
    private val network: IProductNetworkRepository
) :IProductRepository {
    override suspend fun getProducts(): OutputOf<List<ProductConverter>> {
        return try {
            val res = network.getProducts()
            if (res.isSuccessful) {
                val productsResponse = res.body()!!
                val products = productsResponse.map {
                    ProductConverter.fromProductResponse(it)
                }
                val productEntities = products.map {
                    it.toProductEntity()
                }
                local.addProducts(productEntities)
                OutputOf.Success(productEntities.map {
                    ProductConverter.fromProductEntity(it)
                })
            } else {
                val products = local.getProducts()?.map {
                    ProductConverter.fromProductEntity(it)
                } ?: listOf()
                OutputOf.Error.ResponseError(products)
            }
        } catch (e: UnknownHostException) {
            val products = local.getProducts()?.map {
                ProductConverter.fromProductEntity(it)
            } ?: listOf()
            OutputOf.Error.InternetError(products)
        }
    }

    override suspend fun getProductById(id: String): OutputOf<ProductConverter?> {
        return try {
            val res = network.getProduct(id)
            if (res.isSuccessful) {
                val productResponse = res.body()!!
                val product = ProductConverter.fromProductResponse(productResponse)
                val productEntity = local.getProduct(product.id)
                if(productEntity != null){
                    OutputOf.Success(ProductConverter.fromProductEntity(productEntity))
                }
                else{
                    OutputOf.Error.NotFoundError()
                }

            } else {
                val entity = local.getProduct(id)
                val product = if (entity != null) {
                    ProductConverter.fromProductEntity(entity)
                } else {
                    null
                }
                OutputOf.Error.ResponseError(product)
            }
        } catch (e: UnknownHostException) {
            val entity = local.getProduct(id)
            val product = if (entity != null) {
                ProductConverter.fromProductEntity(entity)
            } else {
                null
            }
            OutputOf.Error.InternetError(product)
        }
    }

    override suspend fun updateProduct(product: ProductConverter): ProductConverter? {
        return try {
            val productEntity = local.getProduct(product.id)
            if (productEntity != null) {
                productEntity.isLike = if (product.isLike) {
                    1
                } else {
                    0
                }
                local.updateProduct(productEntity)
                ProductConverter.fromProductEntity(productEntity)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("update artifact error", e.toString())
            null
        }
    }

    override suspend fun getLikedProducts(): List<ProductConverter> {
        return try {
            local.getLikedProducts()?.map {
                ProductConverter.fromProductEntity(it)
            } ?: listOf()
        } catch (e: Exception) {
            Log.e("liked artifacts error", e.toString())
            listOf()
        }
    }

    override suspend fun dislikeProducts(): Boolean {
        return try {
            local.dislikeProducts()
            true
        } catch (e: Exception) {
            Log.e("dislike artifacts error", e.toString())
            false
        }
    }
}