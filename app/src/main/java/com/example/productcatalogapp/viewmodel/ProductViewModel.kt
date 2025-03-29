//
//
//package com.example.productcatalogapp.viewmodel
//
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.http.GET
//import com.example.productcatalogapp.model.Product
//
//// Define API Service
//interface ApiService {
//    @GET("products")
//    suspend fun getProducts(): ProductResponse
//
//    companion object {
//        fun create(): ApiService {
//            return Retrofit.Builder()
//                .baseUrl("https://dummyjson.com/") // ✅ Correct API URL
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(ApiService::class.java)
//        }
//    }
//}
//
//// Define API Response Model
//data class ProductResponse(val products: List<Product>)
//
//// ViewModel with Error Handling & Retry
//class ProductViewModel(private val apiService: ApiService = ApiService.create()) : ViewModel() {
//
//    private val _products = MutableStateFlow<List<Product>>(emptyList())
//    val products: StateFlow<List<Product>> = _products
//
//    private val _loading = MutableStateFlow(false)
//    val loading: StateFlow<Boolean> = _loading
//
//    private val _error = MutableStateFlow<String?>(null)
//    val error: StateFlow<String?> = _error
//
//    init {
//        fetchProducts()
//    }
//
//    fun fetchProducts() {
//        viewModelScope.launch {
//            _loading.value = true
//            _error.value = null
//            try {
//                val response = apiService.getProducts()
//                _products.value = response.products
//                Log.d("API_SUCCESS", "Fetched ${response.products.size} products")
//            } catch (e: Exception) {
//                _error.value = "Failed to fetch products. Tap to retry."
//                Log.e("API_ERROR", "Error fetching products: ${e.message}")
//            } finally {
//                _loading.value = false
//            }
//        }
//    }
//}
//
//


package com.example.productcatalogapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productcatalogapp.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.IOException

// Define API Service
interface ApiService {
    @GET("products")
    suspend fun getProducts(): ProductResponse

    companion object {
        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl("https://dummyjson.com/") // ✅ Correct API URL
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}

// Define API Response Model
data class ProductResponse(val products: List<Product>)

// ViewModel with Error Handling & Retry
class ProductViewModel(private val apiService: ApiService = ApiService.create()) : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val response = apiService.getProducts()
                _products.value = response.products
                Log.d("API_SUCCESS", "Fetched ${response.products.size} products")
            } catch (e: IOException) {
                _error.value = "Network error. Please check your internet connection."
                Log.e("API_ERROR", "Network error: ${e.message}")
            } catch (e: Exception) {
                _error.value = "Failed to fetch products. Tap to retry."
                Log.e("API_ERROR", "Error fetching products: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }
}
