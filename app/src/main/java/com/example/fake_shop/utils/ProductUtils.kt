package com.example.fake_shop.utils

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.example.fake_shop.R
import com.example.fake_shop.data.models.Product
import com.example.fake_shop.utils.ImageUtils.getImageFromUrl
import com.example.fake_shop.utils.ImageUtils.saveImageToExternalStorage
import com.squareup.picasso.Picasso

object ProductUtils {
    suspend fun downloadImage(context: Context, product: Product): Boolean {
        val image = getImageFromUrl(product.image)
        return if (image != null) {
            saveImageToExternalStorage(context, "product", image)
        } else {
            false
        }
    }
    fun loadImageToImageView(imageUrl:String,imageView:ImageView){
        try {
            Picasso.get().load(imageUrl)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.broken_image)
                .into(imageView)
        } catch (ex: Exception) {
            Log.e("Error", ex.message.toString())
            ex.printStackTrace()
        }
    }
}