package com.craftxcode.gpacalculatorbscs6th.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.craftxcode.gpacalculatorbscs6th.domain.calculator.GPAMathematics

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CGPACalculatorScreen(
    onBack: () -> Unit
) {
    var prevCgpa by remember { mutableStateOf("") }
    var prevCredits by remember { mutableStateOf("") }
    var currGpa by remember { mutableStateOf("") }
    var currCredits by remember { mutableStateOf("") }
    
    val pCgpa = prevCgpa.toDoubleOrNull() ?: 0.0
    val pCred = prevCredits.toDoubleOrNull() ?: 0.0
    val cGpa = currGpa.toDoubleOrNull() ?: 0.0
    val cCred = currCredits.toDoubleOrNull() ?: 0.0
    
    val newCgpa = GPAMathematics.calculateNewCGPA(pCgpa, pCred, cGpa, cCred)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Previous CGPA Calc") },
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
            Text("Enter Details", style = MaterialTheme.typography.titleMedium)
            
            OutlinedTextField(
                value = prevCgpa,
                onValueChange = { prevCgpa = it },
                label = { Text("Previous CGPA") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            OutlinedTextField(
                value = prevCredits,
                onValueChange = { prevCredits = it },
                label = { Text("Previous Credits Completed") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            OutlinedTextField(
                value = currGpa,
                onValueChange = { currGpa = it },
                label = { Text("Current Semester GPA") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            OutlinedTextField(
                value = currCredits,
                onValueChange = { currCredits = it },
                label = { Text("Current Semester Credits") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            
            Card(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Calculated New CGPA", style = MaterialTheme.typography.labelLarge)
                    Text(
                        GPAMathematics.formatValue(newCgpa),
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text("Total Credits: ${(pCred + cCred).toInt()}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
