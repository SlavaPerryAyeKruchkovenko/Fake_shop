package com.example.fake_shop.ui.follows

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fake_shop.data.converters.ProductConverter
import com.example.fake_shop.data.models.Product
import com.example.fake_shop.domain.interfaces.IDislikeProductUseCase
import com.example.fake_shop.domain.interfaces.IDislikeProductsUseCase
import com.example.fake_shop.domain.interfaces.IGetLikedProductsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FollowsViewModel(
    private val getLikedProducts: IGetLikedProductsUseCase,
    private val dislikeProduct: IDislikeProductUseCase,
    private val dislikeProducts: IDislikeProductsUseCase,
) : ViewModel() {
    val liveData = MutableLiveData<List<Product>>()
    fun init() {
        updateLiked()
    }

    fun dislikeOneProduct(product: Product) {
        if (product.isLike) {
            viewModelScope.launch {
                dislikeProduct(ProductConverter.fromProduct(product))
                updateLiked()
            }
        }
    }

    fun dislikeAllProduct() {
        viewModelScope.launch {
            dislikeProducts()
            updateLiked()
        }
    }

    private fun updateLiked() {
        viewModelScope.launch {
            try {
                val likedProducts = withContext(Dispatchers.IO) {
                    getLikedProducts()
                }
                Log.d("test",likedProducts.toString())
                liveData.postValue(likedProducts.map {
                    it.toProduct()
                })
            } catch (e: Exception) {
                Log.e("fatal error", e.message ?: "Fatal error")
            }
        }
    }
}