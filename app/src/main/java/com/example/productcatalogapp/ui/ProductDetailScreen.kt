////package com.example.productcatalogapp.ui
////
////import androidx.compose.foundation.Image
////import androidx.compose.foundation.layout.*
////import androidx.compose.foundation.lazy.LazyRow
////import androidx.compose.foundation.lazy.items
////import androidx.compose.material.*
////import androidx.compose.runtime.*
////import androidx.compose.ui.Alignment
////import androidx.compose.ui.Modifier
////import androidx.compose.ui.layout.ContentScale
////import androidx.compose.ui.unit.dp
////import coil.compose.rememberAsyncImagePainter
////import com.example.productcatalogapp.viewmodel.ProductViewModel
////import com.example.productcatalogapp.model.Product
////
////@Composable
////fun ProductDetailScreen(productId: Int, viewModel: ProductViewModel) {
////    val product = viewModel.products.collectAsState().value.find { it.id == productId }
////
////    if (product != null) {
////        Scaffold(
////            topBar = { TopAppBar(title = { Text(product.title) }) }
////        ) {
////            Column(modifier = Modifier.padding(16.dp)) {
////                LazyRow {
////                    items(product.images) { imageUrl ->
////                        Image(
////                            painter = rememberAsyncImagePainter(imageUrl),
////                            contentDescription = "Product Image",
////                            modifier = Modifier
////                                .size(250.dp)
////                                .padding(4.dp),
////                            contentScale = ContentScale.Crop
////                        )
////                    }
////                }
////                Spacer(modifier = Modifier.height(8.dp))
////
////                // ✅ Fix category and rating references
////                Text(text = "Category: ${product.category}", style = MaterialTheme.typography.body1)
////                Text(text = "Price: $${product.price}", style = MaterialTheme.typography.h6)
////                Text(text = "⭐ ${product.rating}", style = MaterialTheme.typography.body2)  // FIXED
////                Text(text = product.description, style = MaterialTheme.typography.body2)
////            }
////        }
////    } else {
////        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
////            Text(text = "Product not found", style = MaterialTheme.typography.h6)
////        }
////    }
////}
//
//
package com.example.productcatalogapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.productcatalogapp.viewmodel.ProductViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(productId: Int, viewModel: ProductViewModel) {
    val product = viewModel.products.collectAsState().value.find { it.id == productId }

    if (product != null) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = product.title, fontWeight = FontWeight.Bold) })
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.elevatedCardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        LazyRow {
                            items(product.images) { imageUrl ->
                                Image(
                                    painter = rememberAsyncImagePainter(imageUrl),
                                    contentDescription = "Product Image",
                                    modifier = Modifier
                                        .size(250.dp)
                                        .padding(4.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Category: ${product.category}", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                        Text(text = "Price: $${product.price}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text(text = "⭐ ${product.rating}", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = product.description, fontSize = 16.sp)
                    }
                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Product not found", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
        }
    }
}


