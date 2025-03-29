package com.example.productcatalogapp.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.productcatalogapp.viewmodel.ProductViewModel


@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: ProductViewModel,
    darkModeEnabled: Boolean,
    onDarkModeToggle: () -> Unit,
    selectedLanguage: String,
    onLanguageChange: (String) -> Unit
) {
    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") { SplashScreen(navController) }

        composable("product_list",enterTransition = { fadeIn(animationSpec = tween(500)) },
            exitTransition = { fadeOut(animationSpec = tween(500)) }) {
            ProductListScreen(navController, viewModel)
        }

        composable(
            "product_detail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductDetailScreen(productId, viewModel)
        }

        // 🔹 Settings Screen (Pass dark mode & language state)
        composable("settings") {
            SettingsScreen(
                darkModeEnabled = darkModeEnabled,
                onDarkModeToggle = onDarkModeToggle,
                selectedLanguage = selectedLanguage,
                onLanguageChange = onLanguageChange
            )
        }
    }
}


