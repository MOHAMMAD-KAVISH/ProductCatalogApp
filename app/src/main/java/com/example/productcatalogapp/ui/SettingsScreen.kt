package com.example.productcatalogapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.productcatalogapp.R

@OptIn(ExperimentalMaterial3Api::class)


@Composable
fun SettingsScreen(
    darkModeEnabled: Boolean,
    onDarkModeToggle: () -> Unit,
    selectedLanguage: String,
    onLanguageChange: (String) -> Unit
) {
    val languages = listOf("English", "हिन्दी", "Español", "Français", "Deutsch")
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(id = R.string.settings)) }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                // 🔹 Dark Mode Switch
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = R.string.dark_mode), fontSize = 18.sp, modifier = Modifier.weight(1f))
                    Switch(checked = darkModeEnabled, onCheckedChange = { onDarkModeToggle() })
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 🔹 Language Selection
                Text(text = stringResource(id = R.string.select_language), fontSize = 18.sp)
                Box {
                    Button(
                        onClick = { expanded = true },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(selectedLanguage)
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        languages.forEach { language ->
                            DropdownMenuItem(
                                text = { Text(language) },
                                onClick = {
                                    onLanguageChange(language)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            // 🔹 Footer
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "© 2025 Mohammad Kavish", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(text = stringResource(id = R.string.all_rights_reserved), fontSize = 14.sp)
                }
            }
        }
    }
}
