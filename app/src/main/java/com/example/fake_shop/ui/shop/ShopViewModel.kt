package com.example.fake_shop.ui.shop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fake_shop.data.models.OutputOf
import com.example.fake_shop.data.models.Product
import com.example.fake_shop.domain.interfaces.IGetProductsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShopViewModel(
    private val getProducts: IGetProductsUseCase,
) : ViewModel() {
    val liveData = MutableLiveData<OutputOf<List<Product>>>()
    private var products: List<Product> = listOf()
    fun init() {
        updateProducts()
    }

    fun updateProducts() {
        liveData.postValue(OutputOf.Loader())
        viewModelScope.launch {
            try {
                val productsResult = withContext(Dispatchers.IO) {
                    getProducts()
                }
                liveData.postValue(
                    when (productsResult) {
                        is OutputOf.Success -> {
                            if (productsResult.value.isNotEmpty()) {
                                val productCopy = productsResult.value.map {
                                    it.toProduct()
                                }
                                products = productCopy
                                OutputOf.Success(products)
                            } else OutputOf.Error.NotFoundError()
                        }
                        is OutputOf.Error.ResponseError -> {
                            val productCopy = productsResult.value.map {
                                it.toProduct()
                            }
                            products = productCopy
                            OutputOf.Success(products)
                        }
                        is OutputOf.Error.InternetError -> {
                            OutputOf.Error.InternetError(productsResult.value.map {
                                it.toProduct()
                            })
                        }
                        else -> OutputOf.Error.NotFoundError()
                    })
            } catch (e: Exception) {
                liveData.postValue(OutputOf.Failure(e.message ?: "Fatal error"))
            }
        }
    }
    fun filterProductsByTitle(query: String) {
        val result = products.filter { x -> x.title.lowercase().startsWith(query.lowercase()) }
        liveData.postValue(
            if (result.isNotEmpty())
                OutputOf.Success(result)
            else
                OutputOf.Error.NotFoundError()
        )
    }
}