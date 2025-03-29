package com.example.productcatalogapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    darkModeEnabled: Boolean,
    onDarkModeToggle: () -> Unit,
    selectedLanguage: String,
    onLanguageChange: (String) -> Unit,
    selectedTheme: String,
    onThemeChange: (String) -> Unit,
    fontSize: Float,
    onFontSizeChange: (Float) -> Unit,
    onResetDefaults: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

        // 🌈 Colorful Top App Bar
        TopAppBar(
            title = { Text("⚙️ Settings", fontSize = 20.sp, color = Color.White) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF6200EE), // Purple Theme
                titleContentColor = Color.White
            ),
            modifier = Modifier.background(Color(0xFF6200EE))
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 🌙 Dark Mode Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color(0xFFBB86FC), shape = RoundedCornerShape(10.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Dark Mode", color = Color.White, fontSize = 18.sp, modifier = Modifier.weight(1f))
            Switch(checked = darkModeEnabled, onCheckedChange = { onDarkModeToggle() })
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 🎨 Theme Selector
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color(0xFF03DAC5), shape = RoundedCornerShape(10.dp))
                .padding(16.dp)
        ) {
            Text("Select Theme", color = Color.White, fontSize = 18.sp)
            DropdownMenuComponent(selectedTheme, onThemeChange, listOf("Light", "Dark", "System Default"))
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 🌍 Language Selector
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color(0xFF018786), shape = RoundedCornerShape(10.dp))
                .padding(16.dp)
        ) {
            Text("🌍 Select Language", color = Color.White, fontSize = 18.sp)
            DropdownMenuComponent(selectedLanguage, onLanguageChange, listOf("English", "हिन्दी 🇮🇳", "Español 🇪🇸", "Français 🇫🇷", "Deutsch 🇩🇪"))
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 🔄 Reset Button
        Button(
            onClick = onResetDefaults,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Reset to Defaults", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun DropdownMenuComponent(selectedOption: String, onOptionSelected: (String) -> Unit, options: List<String>) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(selectedOption, color = Color.Black)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}



