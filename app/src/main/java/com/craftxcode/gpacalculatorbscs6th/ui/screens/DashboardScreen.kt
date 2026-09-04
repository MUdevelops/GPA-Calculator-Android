package com.craftxcode.gpacalculatorbscs6th.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.craftxcode.gpacalculatorbscs6th.domain.calculator.GPAMathematics
import com.craftxcode.gpacalculatorbscs6th.ui.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onNavigateToCalculate: () -> Unit,
    onNavigateToCgpa: () -> Unit,
    onNavigateToRecords: () -> Unit,
    onNavigateToScale: () -> Unit,
) {
    val stats by viewModel.stats.collectAsState()
    val config = LocalConfiguration.current
    val isWide = config.screenWidthDp > 600

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("GPA786", fontWeight = FontWeight.Bold)
                        Text("GPA & CGPA Calculator", style = MaterialTheme.typography.bodySmall)
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Welcome, BSCS Friend! 👋",
                    style = if (isWide) MaterialTheme.typography.displaySmall else MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatCard(
                        title = "Current GPA",
                        value = GPAMathematics.formatValue(stats.currentGPA),
                        modifier = Modifier.weight(1f),
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                    StatCard(
                        title = "Overall CGPA",
                        value = GPAMathematics.formatValue(stats.overallCGPA),
                        modifier = Modifier.weight(1f),
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                    if (isWide) {
                        StatCard(
                            title = "Credits",
                            value = stats.totalCredits.toInt().toString(),
                            modifier = Modifier.weight(1f),
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    }
                }
            }

            if (!isWide) {
                item {
                    StatCard(
                        title = "Total Credits Completed",
                        value = stats.totalCredits.toInt().toString(),
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                }
            }

            item {
                Text(
                    text = "Quick Actions",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            item {
                val actions = listOf(
                    Triple("Calculate GPA", Icons.Default.Calculate, onNavigateToCalculate),
                    Triple("Calculate CGPA", Icons.AutoMirrored.Filled.TrendingUp, onNavigateToCgpa),
                    Triple("Academic Records", Icons.Default.History, onNavigateToRecords),
                    Triple("Grade Scale", Icons.Default.Grade, onNavigateToScale)
                )
                
                if (isWide) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        actions.chunked(2).forEach { chunk ->
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                chunk.forEach { (title, icon, onClick) ->
                                    ActionCard(
                                        title = title,
                                        icon = icon,
                                        onClick = onClick,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                if (chunk.size == 1) Spacer(Modifier.weight(1f))
                            }
                        }
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        actions.forEach { (title, icon, onClick) ->
                            ActionCard(
                                title = title,
                                icon = icon,
                                onClick = onClick
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, style = MaterialTheme.typography.labelMedium, maxLines = 1)
            Text(
                value,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionCard(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
        }
    }
}
