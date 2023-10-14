package com.example.fake_shop.utils

import android.graphics.Bitmap
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

suspend fun getImageFromUrl(imageUrl: String): Bitmap? = withContext(Dispatchers.IO) {
    try {
        Picasso.get()
            .load(imageUrl)
            .get()
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}