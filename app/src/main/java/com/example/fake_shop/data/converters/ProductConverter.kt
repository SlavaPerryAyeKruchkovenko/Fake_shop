package com.example.fake_shop.data.converters

import com.example.fake_shop.data.models.Product
import com.example.fake_shop.data.responses.ProductResponse
import com.example.fake_shop.database.entities.ProductEntity

class ProductConverter(
    val id: String,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val count: Int,
    val rating: Double,
    var isLike: Boolean,
    var comment: String?
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
            this.isLike,
            this.comment
        )
    }

    fun toProductEntity(): ProductEntity {
        val isLike = if (this.isLike) {
            1
        } else {
            0
        }
        return ProductEntity(
            this.id,
            this.title,
            this.price,
            this.description,
            this.category,
            this.count,
            this.rating,
            this.image,
            isLike,
            this.comment
        )
    }

    override fun toString(): String {
        return "id: $id, title: $title, isLike: $isLike"
    }
    companion object {
        fun fromProductResponse(res: ProductResponse): ProductConverter {
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
                null
            )
        }

        fun fromProduct(product: Product): ProductConverter {
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
                product.comment
            )
        }

        fun fromProductEntity(entity: ProductEntity): ProductConverter {
            val isLike = entity.isLike > 0
            return ProductConverter(
                entity.id,
                entity.title,
                entity.price,
                entity.description,
                entity.category,
                entity.image,
                entity.count,
                entity.rating,
                isLike,
                entity.comment
            )
        }
    }
}