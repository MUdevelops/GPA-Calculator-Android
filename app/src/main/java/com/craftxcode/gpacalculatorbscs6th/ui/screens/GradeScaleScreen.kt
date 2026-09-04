package com.craftxcode.gpacalculatorbscs6th.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.craftxcode.gpacalculatorbscs6th.domain.calculator.GPAMathematics
import com.craftxcode.gpacalculatorbscs6th.domain.calculator.GradeCalculator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradeScaleScreen(
    onBack: () -> Unit
) {
    val scale = GradeCalculator.defaultScale

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Grade Scale") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Grade", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text("Percentage", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1.5f))
                    Text("GPA", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                }
                HorizontalDivider()
            }
            items(scale.entries) { entry ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(entry.grade, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
                    val rangeText = if (entry.minPercentage == 0.0) "< 40%" else "${entry.minPercentage.toInt()}%+"
                    Text(rangeText, modifier = Modifier.weight(1.5f))
                    Text(GPAMathematics.formatValue(entry.gradePoint), modifier = Modifier.weight(1f))
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            }
        }
    }
}
