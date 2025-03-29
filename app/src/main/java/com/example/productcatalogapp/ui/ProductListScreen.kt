package com.example.productcatalogapp.ui

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.productcatalogapp.model.Product
import com.example.productcatalogapp.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(navController: NavController, viewModel: ProductViewModel) {
    val products by viewModel.products.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val filteredProducts = products.filter { it.title.contains(searchQuery, ignoreCase = true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product List", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                modifier = Modifier.background(
                    Brush.horizontalGradient(listOf(Color(0xFF6A11CB), Color(0xFF2575FC)))
                ),
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = Color.White)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // Search Bar
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search products...", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFF5F5F5), // Background color
                    focusedIndicatorColor = Color(0xFF6A11CB), // Focused indicator color
                    unfocusedIndicatorColor = Color.Gray, // Unfocused indicator color
                    focusedTextColor = Color.Black, // Focused text color
                    unfocusedTextColor = Color.Black // Unfocused text color
                )
            )

            // Show loading indicator
            if (loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            // Show error message with retry option
            if (error != null) {
                Text(
                    text = error!!,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Button(
                    onClick = { viewModel.fetchProducts() },
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp)
                ) {
                    Text("Retry")
                }
            }

            // Show products if no error
            if (error == null && !loading) {
                LazyColumn {
                    items(filteredProducts) { product ->
                        ProductItem(product, onClick = {
                            navController.navigate("product_detail/${product.id}")
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    var isLoading by remember { mutableStateOf(true) }

    // Delay to simulate loading time for images
    LaunchedEffect(product.images) {
        isLoading = false
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(10.dp, shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Color.Gray) // Placeholder for image loading
            ) {
                if (!isLoading) {
                    Image(
                        painter = rememberImagePainter(
                            data = product.images.firstOrNull(),
                            builder = {
                                crossfade(true)
                                placeholder(android.R.drawable.ic_menu_report_image)
                                error(android.R.drawable.stat_notify_error)
                            }
                        ),
                        contentDescription = "Product Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Product Title
            Text(
                text = product.title,
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF6A11CB),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            // Price
            Text(
                text = "Price: $${product.price}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF2575FC),
                fontWeight = FontWeight.Bold
            )

            // Description
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            // Hidden watermark
            Text(
                text = "Designed by Mohammad Kavish",
                fontSize = 8.sp,
                modifier = Modifier.alpha(0.1f) // Hidden watermark
            )
        }
    }
}
