package com.example.fake_shop.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import java.io.IOException

fun savePhotoToExternalStorage(context: Context, fileName:String, photo: Bitmap): Boolean {
    val imageCollection = sdk29AndUp {
        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
    } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "$fileName.png")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.WIDTH, photo.width)
        put(MediaStore.Images.Media.HEIGHT, photo.height)
    }
    val contentResolver = context.contentResolver
    return try {
        contentResolver.insert(imageCollection, contentValues)?.also { uri ->
            contentResolver.openOutputStream(uri).use { outputStream ->
                if(outputStream != null){
                    if(!photo.compress(Bitmap.CompressFormat.PNG, 95, outputStream)) {
                        throw IOException("Couldn't save bitmap")
                    }
                }
            }
        } ?: throw IOException("Couldn't create MediaStore entry")
        true
    } catch(e: IOException) {
        false
    }
}