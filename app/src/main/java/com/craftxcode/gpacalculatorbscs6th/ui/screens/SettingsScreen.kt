package com.craftxcode.gpacalculatorbscs6th.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("General", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            
            ListItem(
                headlineContent = { Text("Theme") },
                supportingContent = { Text("System Default") },
                leadingContent = { Icon(Icons.Default.Palette, contentDescription = null) }
            )
            
            HorizontalDivider()
            
            Text("About", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            
            ListItem(
                headlineContent = { Text("GPA786") },
                supportingContent = { Text("GPA & CGPA Calculator for BSCS Students") },
                leadingContent = { Icon(Icons.Default.Info, contentDescription = null) }
            )
            
            Column(modifier = Modifier.fillMaxWidth().padding(top = 32.dp)) {
                Text("Built by Your Colleague", style = MaterialTheme.typography.labelMedium)
                Text("M. Umar Jamal", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("Welcome BSCS Friends", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
