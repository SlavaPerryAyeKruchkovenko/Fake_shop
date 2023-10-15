package com.example.fake_shop.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object ImageUtils {
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

    suspend fun getImageToShare(context: Context, bitmap: Bitmap): Uri? =
        withContext(Dispatchers.IO) {
            val folder = File(context.cacheDir, "images")
            try {
                folder.mkdirs()
                val file = File(folder, "shared_image.png")
                val fileStream = FileOutputStream(file)
                val couldSave = bitmap.compress(Bitmap.CompressFormat.PNG, 95, fileStream)
                if (couldSave) {
                    fileStream.flush()
                    fileStream.close()
                    FileProvider.getUriForFile(context, "com.example.fake_shop", file)
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }

    fun saveImageToExternalStorage(context: Context, fileName: String, photo: Bitmap): Boolean {
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
                    if (outputStream != null) {
                        if (!photo.compress(Bitmap.CompressFormat.PNG, 95, outputStream)) {
                            throw IOException("Couldn't save bitmap")
                        }
                    }
                }
            } ?: throw IOException("Couldn't create MediaStore entry")
            true
        } catch (e: IOException) {
            false
        }
    }
}