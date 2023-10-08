package com.example.fake_shop.ui.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fake_shop.data.converters.ProductConverter
import com.example.fake_shop.data.models.OutputOf
import com.example.fake_shop.data.models.Product
import com.example.fake_shop.domain.interfaces.IDislikeProductUseCase
import com.example.fake_shop.domain.interfaces.IGetProductUseCase
import com.example.fake_shop.domain.interfaces.ILikeProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel(
    private val getProduct: IGetProductUseCase,
    private val likeProduct: ILikeProductUseCase,
    private val dislikeProduct: IDislikeProductUseCase,
) : ViewModel() {
    val productLiveData = MutableLiveData<OutputOf<Product?>>()
    val isLikedLiveData = MutableLiveData(false)
    fun init(productId: String) {
        productLiveData.postValue(OutputOf.Loader())
        viewModelScope.launch {
            val productResult = withContext(Dispatchers.IO) {
                getProduct(productId)
            }
            productLiveData.postValue(
                when (productResult) {
                    is OutputOf.Success -> {
                        if (productResult.value != null) {
                            val product = productResult.value.toProduct()
                            isLikedLiveData.postValue(product.isLike)
                            OutputOf.Success(product)
                        } else OutputOf.Error.NotFoundError()
                    }
                    is OutputOf.Error.ResponseError -> {
                        if (productResult.value != null) {
                            val product = productResult.value.toProduct()
                            isLikedLiveData.postValue(product.isLike)
                            OutputOf.Error.ResponseError(product)
                        }
                        else{
                            OutputOf.Error.ResponseError(null)
                        }
                    }
                    is OutputOf.Error.InternetError -> {
                        if (productResult.value != null) {
                            val product = productResult.value.toProduct()
                            isLikedLiveData.postValue(product.isLike)
                            OutputOf.Error.InternetError(product)
                        }
                        else{
                            OutputOf.Error.InternetError(null)
                        }
                    }
                    else -> OutputOf.Error.NotFoundError()
                })
        }
    }

    fun changeLike() {
        viewModelScope.launch {
            if (productLiveData.value is OutputOf.Success || productLiveData.value is OutputOf.Error.InternetError) {
                val product = (productLiveData.value as OutputOf.Success<Product?>).value
                if (product != null) {
                    val productConvert = withContext(Dispatchers.IO) {
                        if (product.isLike) {
                            dislikeProduct(ProductConverter.fromProduct(product))
                        } else {
                            likeProduct(ProductConverter.fromProduct(product))
                        }
                    }
                    if (productConvert != null) {
                        val newProduct = productConvert.toProduct()
                        isLikedLiveData.postValue(newProduct.isLike)
                        productLiveData.postValue(OutputOf.Success(newProduct))
                    } else {
                        productLiveData.postValue(OutputOf.Error.NotFoundError())
                    }

                }
            }
        }
    }
}