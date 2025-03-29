package com.example.productcatalogapp

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.example.productcatalogapp.ui.NavGraph
import com.example.productcatalogapp.viewmodel.ProductViewModel
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ProductViewModel()

        setContent {
            var darkModeEnabled by remember { mutableStateOf(false) }
            var selectedLanguage by remember { mutableStateOf("English") }

            MaterialTheme(colorScheme = if (darkModeEnabled) darkColorScheme() else lightColorScheme()) {
                Surface {
                    val navController = rememberNavController()

                    NavGraph(
                        navController = navController,
                        viewModel = viewModel,
                        darkModeEnabled = darkModeEnabled,
                        onDarkModeToggle = { darkModeEnabled = !darkModeEnabled },
                        selectedLanguage = selectedLanguage,
                        onLanguageChange = { newLanguage ->
                            selectedLanguage = newLanguage
                            updateAppLocale(this@MainActivity, newLanguage)
                        }
                    )
                }
            }
        }
    }

    private fun updateAppLocale(context: Context, language: String) {
        val locale = when (language) {
            "हिन्दी" -> Locale("hi")
            "Español" -> Locale("es")
            "Français" -> Locale("fr")
            "Deutsch" -> Locale("de")
            else -> Locale("en")
        }

        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        recreate() // Refresh activity to apply changes
    }
}




