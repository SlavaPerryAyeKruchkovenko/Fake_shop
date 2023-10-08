package com.example.fake_shop.ui.shop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fake_shop.data.models.OutputOf
import com.example.fake_shop.data.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShopViewModel(): ViewModel() {
    val liveData = MutableLiveData<OutputOf<List<Product>>>()
    private var liveDataCopy: List<Product> = listOf()
    fun init() {
        updateProducts()
    }
    fun updateProducts(){
        liveData.postValue(OutputOf.Loader())
        viewModelScope.launch {
            try {
                /*val products = withContext(Dispatchers.IO) {
                    getWeapons()
                }
                liveData.postValue(
                    if (products.isNotEmpty())
                        OutputOf.Success(liveData)
                    else
                        OutputOf.Error.NotFoundError()
                )*/
            } catch (e: Exception) {
                liveData.postValue(OutputOf.Failure(e.message ?: "Fatal error"))
            }
        }
    }
    fun filterProductsByTitle(query: String) {
        val result = liveDataCopy.filter { x -> x.title.lowercase().startsWith(query.lowercase()) }
        liveData.postValue(
            if (result.isNotEmpty())
                OutputOf.Success(result)
            else
                OutputOf.Error.NotFoundError()
        )
    }
}