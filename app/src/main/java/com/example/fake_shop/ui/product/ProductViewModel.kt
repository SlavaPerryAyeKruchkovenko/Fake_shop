package com.example.fake_shop.ui.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fake_shop.data.models.OutputOf
import com.example.fake_shop.data.models.Product
import com.example.fake_shop.domain.interfaces.IGetProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel(private val getProduct: IGetProductUseCase) : ViewModel() {
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
            if (productLiveData.value is OutputOf.Success) {
                if (productLiveData.value != null) {
                    val product = (productLiveData.value as OutputOf.Success<Product?>).value
                    /*val characterConvert = withContext(Dispatchers.IO) {
                        if (character.isLike) {
                            dislikeCharacter(CharacterConvert.fromCharacter(character))
                        } else {
                            likeCharacter(CharacterConvert.fromCharacter(character))
                        }
                    }
                    val newCharacter = characterConvert.toCharacter()
                    isLikedLiveData.postValue(newCharacter.isLike)
                    productLiveData.postValue(newCharacter)*/
                }
            }
        }
    }
}