package com.craftxcode.gpacalculatorbscs6th.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.craftxcode.gpacalculatorbscs6th.data.entity.SemesterWithCourses
import com.craftxcode.gpacalculatorbscs6th.domain.calculator.GPAMathematics
import com.craftxcode.gpacalculatorbscs6th.ui.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcademicRecordsScreen(
    viewModel: DashboardViewModel,
    onBack: () -> Unit
) {
    val semesters by viewModel.semesters.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Academic Records") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (semesters.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("No academic records yet.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(semesters) { item ->
                    SemesterRecordItem(item)
                }
            }
        }
    }
}

@Composable
fun SemesterRecordItem(item: SemesterWithCourses) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(item.semester.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(
                    GPAMathematics.formatValue(item.semester.gpa),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Text("Credits: ${item.semester.totalCredits.toInt()}", style = MaterialTheme.typography.bodyMedium)
            Text("Courses: ${item.courses.size}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
