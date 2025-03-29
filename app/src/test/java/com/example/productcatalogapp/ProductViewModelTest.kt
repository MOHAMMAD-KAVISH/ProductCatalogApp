package com.example.productcatalogapp

import app.cash.turbine.test
import com.example.productcatalogapp.model.Product
import com.example.productcatalogapp.viewmodel.ApiService
import com.example.productcatalogapp.viewmodel.ProductResponse
import com.example.productcatalogapp.viewmodel.ProductViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductViewModelTest {

    // Mock API Service
    private val apiService = mockk<ApiService>()

    // ViewModel instance
    private lateinit var viewModel: ProductViewModel

    @Before
    fun setup() {
        // Mock API Response
        coEvery { apiService.getProducts() } returns ProductResponse(
            listOf(
                Product(
                    id = 1, title = "Laptop", description = "Gaming Laptop", category = "Electronics",
                    price = 1000.0, discountPercentage = 10.0, rating = 4.5, stock = 20,
                    tags = listOf("Tech"), brand = "BrandX", sku = "12345", weight = 2,
                    warrantyInformation = "1 Year", shippingInformation = "Fast",
                    availabilityStatus = "In Stock", returnPolicy = "30 Days",
                    images = listOf("image1"), thumbnail = "thumb1"
                ),
                Product(
                    id = 2, title = "Phone", description = "Smartphone", category = "Electronics",
                    price = 500.0, discountPercentage = 5.0, rating = 4.0, stock = 50,
                    tags = listOf("Mobile"), brand = "BrandY", sku = "67890", weight = 1,
                    warrantyInformation = "2 Years", shippingInformation = "Standard",
                    availabilityStatus = "In Stock", returnPolicy = "30 Days",
                    images = listOf("image2"), thumbnail = "thumb2"
                )
            )
        )

        // Initialize ViewModel
        viewModel = ProductViewModel(apiService)
    }

    @Test
    fun `products StateFlow should update when API call succeeds`() = runTest {
        // Wait for StateFlow to update
        val result = viewModel.products.first()

        // Verify that products match expected data
        assertEquals(2, result.size)
        assertEquals("Laptop", result[0].title)
        assertEquals("Phone", result[1].title)
    }
}
