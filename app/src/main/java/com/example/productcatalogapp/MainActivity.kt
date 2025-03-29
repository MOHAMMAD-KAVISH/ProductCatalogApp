package com.example.productcatalogapp

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
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
            var selectedTheme by remember { mutableStateOf("System Default") }
            var selectedLanguage by remember { mutableStateOf("English") }
            var fontSize by remember { mutableStateOf(16f) }

            // 🎨 Correct Theme Handling
            val isDarkTheme = when (selectedTheme) {
                "Light" -> false
                "Dark" -> true
                else -> isSystemInDarkTheme() || darkModeEnabled
            }

            val colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()

            MaterialTheme(colorScheme = colorScheme) {
                Surface {
                    val navController = rememberNavController()

                    NavGraph(
                        navController = navController,
                        viewModel = viewModel,
                        darkModeEnabled = isDarkTheme,
                        onDarkModeToggle = { darkModeEnabled = !darkModeEnabled },
                        selectedLanguage = selectedLanguage,
                        onLanguageChange = { newLanguage ->
                            selectedLanguage = newLanguage
                            updateAppLocale(this@MainActivity, newLanguage)
                        },
                        selectedTheme = selectedTheme,
                        onThemeChange = { newTheme -> selectedTheme = newTheme },
                        fontSize = fontSize,
                        onFontSizeChange = { newSize -> fontSize = newSize },
                        onResetDefaults = {
                            darkModeEnabled = false
                            selectedTheme = "System Default"
                            selectedLanguage = "English"
                            fontSize = 16f
                        }
                    )
                }
            }
        }
    }

    private fun updateAppLocale(context: Context, language: String) {
        val locale = when (language) {
            "हिन्दी 🇮🇳" -> Locale("hi")
            "Español 🇪🇸" -> Locale("es")
            "Français 🇫🇷" -> Locale("fr")
            "Deutsch 🇩🇪" -> Locale("de")
            else -> Locale("en")
        }
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        recreate() // 🔄 Restart activity to apply changes
    }
}
