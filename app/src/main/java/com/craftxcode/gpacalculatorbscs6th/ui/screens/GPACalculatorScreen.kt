package com.craftxcode.gpacalculatorbscs6th.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.craftxcode.gpacalculatorbscs6th.data.entity.Course
import com.craftxcode.gpacalculatorbscs6th.domain.calculator.GPAMathematics
import com.craftxcode.gpacalculatorbscs6th.domain.calculator.GradeCalculator
import com.craftxcode.gpacalculatorbscs6th.ui.viewmodel.CalculatorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GPACalculatorScreen(
    viewModel: CalculatorViewModel,
    onCalculate: (Double, Double, Double, String) -> Unit,
    onNavigateToWeight: (Int) -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val config = LocalConfiguration.current
    val isWide = config.screenWidthDp > 600
    
    val totalCredits = uiState.courses.sumOf { it.creditHours }
    val totalQP = uiState.courses.sumOf { it.creditHours * it.gradePoint }
    val semesterGPA = GPAMathematics.calculateGPA(totalQP, totalCredits)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calculate GPA") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.addCourse() }) {
                Icon(Icons.Default.Add, contentDescription = "Add Course")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            OutlinedTextField(
                value = uiState.semesterName,
                onValueChange = { viewModel.updateSemesterName(it) },
                label = { Text("Semester Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 340.dp),
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(uiState.courses) { index, course ->
                    CourseItem(
                        course = course,
                        onUpdate = { viewModel.updateCourse(index, it) },
                        onRemove = { viewModel.removeCourse(index) },
                        onCalculateMarks = { onNavigateToWeight(index) }
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp),
                shape = RectangleShape
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Semester GPA", style = MaterialTheme.typography.labelLarge)
                            Text(
                                GPAMathematics.formatValue(semesterGPA),
                                style = MaterialTheme.typography.displaySmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (isWide) {
                                Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(end = 16.dp)) {
                                    Text("Total Credits: ${totalCredits.toInt()}", style = MaterialTheme.typography.bodyMedium)
                                    Text("QP: ${GPAMathematics.formatValue(totalQP)}", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                            Button(
                                onClick = {
                                    val finalGrade = GradeCalculator.calculateGrade(
                                    (semesterGPA / 4.0) * 100, // Map 4.0 to 100 for grade lookup
                                    uiState.gradeScale
                                ).grade
                                    onCalculate(semesterGPA, totalCredits, totalQP, finalGrade)
                                }
                            ) {
                                Text("View Full Result")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CourseItem(
    course: Course,
    onUpdate: (Course) -> Unit,
    onRemove: () -> Unit,
    onCalculateMarks: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = course.name,
                    onValueChange = { onUpdate(course.copy(name = it)) },
                    label = { Text("Course Name") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                IconButton(onClick = onCalculateMarks) {
                    Icon(Icons.Default.Functions, contentDescription = "Calculate Marks")
                }
                IconButton(onClick = onRemove) {
                    Icon(Icons.Default.Delete, contentDescription = "Remove", tint = MaterialTheme.colorScheme.error)
                }
            }
            
            Row(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = if (course.creditHours == 0.0) "" else course.creditHours.toString(),
                    onValueChange = { 
                        val credits = it.toDoubleOrNull() ?: 0.0
                        onUpdate(course.copy(creditHours = credits))
                    },
                    label = { Text("Credits") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                OutlinedTextField(
                    value = if (course.marks == 0.0) "" else course.marks.toString(),
                    onValueChange = { 
                        val marks = it.toDoubleOrNull() ?: 0.0
                        onUpdate(course.copy(marks = marks))
                    },
                    label = { Text("Marks %") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                Box(
                    modifier = Modifier
                        .weight(0.8f)
                        .padding(top = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Grade", style = MaterialTheme.typography.labelSmall)
                        Text(course.grade.ifEmpty { "-" }, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text(GPAMathematics.formatValue(course.gradePoint), style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
