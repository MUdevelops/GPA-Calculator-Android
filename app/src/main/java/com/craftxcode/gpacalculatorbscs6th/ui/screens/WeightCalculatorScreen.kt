package com.craftxcode.gpacalculatorbscs6th.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.craftxcode.gpacalculatorbscs6th.domain.calculator.Component
import com.craftxcode.gpacalculatorbscs6th.domain.calculator.GPAMathematics
import com.craftxcode.gpacalculatorbscs6th.domain.calculator.WeightCalculator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeightCalculatorScreen(
    onCalculate: (Double) -> Unit,
    onBack: () -> Unit
) {
    var components by remember { 
        mutableStateOf(listOf(
            Component("Assignments", 0.0, 10.0, 10.0),
            Component("Quizzes", 0.0, 10.0, 10.0),
            Component("Midterm", 0.0, 30.0, 30.0),
            Component("Final Exam", 0.0, 50.0, 50.0)
        )) 
    }
    
    val totalWeight = components.sumOf { it.weightPercentage }
    val weightedPercentage = WeightCalculator.calculateWeightedPercentage(components)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Weight Calculator") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                components = components + Component("", 0.0, 100.0, 0.0)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Component")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(components) { index, component ->
                    ComponentItem(
                        component = component,
                        onUpdate = { updated ->
                            val newList = components.toMutableList()
                            newList[index] = updated
                            components = newList
                        },
                        onRemove = {
                            val newList = components.toMutableList()
                            newList.removeAt(index)
                            components = newList
                        }
                    )
                }
            }

            Surface(
                tonalElevation = 4.dp,
                shadowElevation = 8.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total Weight: ${totalWeight.toInt()}%")
                        if (totalWeight != 100.0) {
                            Text("Must be 100%", color = MaterialTheme.colorScheme.error)
                        }
                    }
                    Text(
                        "Final Marks: ${GPAMathematics.formatValue(weightedPercentage)}%",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (totalWeight == 100.0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                    )
                    Button(
                        onClick = { onCalculate(weightedPercentage) },
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                        enabled = totalWeight == 100.0
                    ) {
                        Text("Apply to Course")
                    }
                }
            }
        }
    }
}

@Composable
fun ComponentItem(
    component: Component,
    onUpdate: (Component) -> Unit,
    onRemove: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = component.name,
                    onValueChange = { onUpdate(component.copy(name = it)) },
                    label = { Text("Component Name") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                IconButton(onClick = onRemove) {
                    Icon(Icons.Default.Delete, contentDescription = "Remove", tint = MaterialTheme.colorScheme.error)
                }
            }
            Row(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = if (component.obtainedMarks == 0.0) "" else component.obtainedMarks.toString(),
                    onValueChange = { onUpdate(component.copy(obtainedMarks = it.toDoubleOrNull() ?: 0.0)) },
                    label = { Text("Obtained") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                OutlinedTextField(
                    value = if (component.totalMarks == 0.0) "" else component.totalMarks.toString(),
                    onValueChange = { onUpdate(component.copy(totalMarks = it.toDoubleOrNull() ?: 0.0)) },
                    label = { Text("Total") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                OutlinedTextField(
                    value = if (component.weightPercentage == 0.0) "" else component.weightPercentage.toString(),
                    onValueChange = { onUpdate(component.copy(weightPercentage = it.toDoubleOrNull() ?: 0.0)) },
                    label = { Text("Weight %") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }
        }
    }
}
