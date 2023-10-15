package com.example.fake_shop.utils

import android.content.Context
import com.example.fake_shop.data.models.Product
import com.example.fake_shop.utils.ImageUtils.getImageFromUrl
import com.example.fake_shop.utils.ImageUtils.saveImageToExternalStorage

object ProductUtils {
    suspend fun downloadImage(context: Context, product: Product): Boolean {
        val image = getImageFromUrl(product.image)
        return if (image != null) {
            saveImageToExternalStorage(context, "product", image)
        } else {
            false
        }
    }
}