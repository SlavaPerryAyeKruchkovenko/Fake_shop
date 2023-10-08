package com.example.fake_shop.ui.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fake_shop.data.models.OutputOf
import com.example.fake_shop.data.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel() : ViewModel() {
    val productLiveData = MutableLiveData<OutputOf<Product?>>()
    val isLikedLiveData = MutableLiveData(false)
    fun init(productId: String) {
        productLiveData.postValue(OutputOf.Loader())
        viewModelScope.launch {
            /*val productConvert = withContext(Dispatchers.IO) {
                *//*getCharacter(characterId)*//*
            }
            val character = productConvert()
            characterPortrait.postValue(character)
            isLikedLiveData.postValue(character.isLike)*/
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