package com.craftxcode.gpacalculatorbscs6th.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.craftxcode.gpacalculatorbscs6th.domain.calculator.GPAMathematics
import com.craftxcode.gpacalculatorbscs6th.ui.theme.GradientBlue
import com.craftxcode.gpacalculatorbscs6th.ui.theme.GradientPurple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    gpa: Double,
    credits: Double,
    qualityPoints: Double,
    grade: String,
    onSave: () -> Unit,
    onBack: () -> Unit,
    onShare: () -> Unit,
) {
    var startAnim by remember { mutableStateOf(value = false) }
    val animatedGpa by animateFloatAsState(
        targetValue = if (startAnim) gpa.toFloat() else 0f,
        animationSpec = tween(1500),
        label = "gpa"
    )

    LaunchedEffect(Unit) {
        startAnim = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Semester Result") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onShare) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .widthIn(max = 600.dp)
                    .fillMaxWidth()
                    .padding(24.dp),
                elevation = CardDefaults.cardElevation(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "GPA",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                    Text(
                        text = GPAMathematics.formatValue(animatedGpa.toDouble()),
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontSize = 80.sp,
                            fontWeight = FontWeight.Black
                        ),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Badge(
                        containerColor = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.scale(1.5f).padding(top = 8.dp)
                    ) {
                        Text(
                            text = grade,
                            modifier = Modifier.padding(horizontal = 8.dp),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ResultStat("Credits", credits.toInt().toString())
                        ResultStat("Quality Points", GPAMathematics.formatValue(qualityPoints))
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text("Generated by GPA786", style = MaterialTheme.typography.labelMedium)
            Text("Built by Your Colleague M. Umar Jamal", style = MaterialTheme.typography.labelSmall)
            
            Row(
                modifier = Modifier.padding(top = 32.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(onClick = onSave) {
                    Text("Save Result")
                }
                OutlinedButton(onClick = onBack) {
                    Text("Calculate Again")
                }
            }
        }
    }
}

@Composable
fun ResultStat(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, style = MaterialTheme.typography.labelMedium)
        Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
    }
}
