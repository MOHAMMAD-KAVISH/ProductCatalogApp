package com.example.productcatalogapp.ui

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.productcatalogapp.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    var visible by remember { mutableStateOf(false) }

    // Launch Effect for Animation & Navigation
    LaunchedEffect(Unit) {
        visible = true
        delay(3000)  // Splash screen delay
        visible = false
        delay(500)   // Short transition before navigation
        navController.navigate("product_list") {
            popUpTo("splash") { inclusive = true }
        }
    }

    // Splash Screen Layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1B1F3B), Color(0xFF09142F)) // Deep gradient
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // 🔵 **Logo with Glow Effect**
            AnimatedVisibility(visible = visible, enter = fadeIn(), exit = fadeOut()) {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape) // **Rounded Logo**
                        .background(Color.White.copy(alpha = 0.1f)) // Soft glow
                        .padding(10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.splash_logo), // Replace with your actual logo
                        contentDescription = "App Logo",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // Spacer between logo and text

            // 🌟 **App Name & Quote with Shadow**
            AnimatedVisibility(visible = visible, enter = fadeIn(), exit = fadeOut()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "📦 CatalogHub by Kavish",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.shadow(4.dp, CircleShape)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "\"Innovating the Future, One Line at a Time\"",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light,
                        color = Color(0xFFBB86FC), // **Accent color for contrast**
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
