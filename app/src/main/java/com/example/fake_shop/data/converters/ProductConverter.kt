package com.example.fake_shop.data.converters

import com.example.fake_shop.data.models.Product
import com.example.fake_shop.data.responses.ProductResponse

class ProductConverter(
    val id: String,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val count: Int,
    val rating: Double,
    var isLike: Boolean
) {
    fun toProduct(): Product {
        return Product(
            this.id,
            this.title,
            this.price,
            this.description,
            this.category,
            this.image,
            this.count,
            this.rating,
            this.isLike
        )
    }
    fun toArtifactEntity() {

    }
    companion object {
        fun fromArtifactResponse(res: ProductResponse): ProductConverter {
            return ProductConverter(
                res.id,
                res.title,
                res.price,
                res.description,
                res.category,
                res.image,
                res.rating.count,
                res.rating.rate,
                false,
            )
        }
        fun fromArtifact(product:ProductConverter): ProductConverter{
            return ProductConverter(
                product.id,
                product.title,
                product.price,
                product.description,
                product.category,
                product.image,
                product.count,
                product.rating,
                product.isLike,
            )
        }
        fun fromArtifactEntity() {
            /*val isLike = entity.isLike > 0*/
        }
    }
}