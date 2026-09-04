package com.craftxcode.gpacalculatorbscs6th

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.craftxcode.gpacalculatorbscs6th.data.AppDatabase
import com.craftxcode.gpacalculatorbscs6th.data.entity.Course
import com.craftxcode.gpacalculatorbscs6th.data.entity.Semester
import com.craftxcode.gpacalculatorbscs6th.navigation.Screen
import com.craftxcode.gpacalculatorbscs6th.ui.AdaptiveScaffold
import com.craftxcode.gpacalculatorbscs6th.ui.screens.*
import com.craftxcode.gpacalculatorbscs6th.ui.theme.GpaCalculatorBSCS6thTheme
import com.craftxcode.gpacalculatorbscs6th.ui.viewmodel.CalculatorViewModel
import com.craftxcode.gpacalculatorbscs6th.ui.viewmodel.DashboardViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val db = AppDatabase.getDatabase(this)
        val semesterDao = db.semesterDao()

        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            GpaCalculatorBSCS6thTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val dashboardViewModel = DashboardViewModel(semesterDao)
                    val calculatorViewModel: CalculatorViewModel = viewModel()
                    
                    val navController = rememberNavController()
                    AdaptiveScaffold(
                        windowWidthSizeClass = windowSizeClass.widthSizeClass,
                        navController = navController
                    ) { padding ->
                        AppNavigation(
                            navController = navController,
                            padding = padding,
                            dashboardViewModel = dashboardViewModel,
                            calculatorViewModel = calculatorViewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    padding: PaddingValues,
    dashboardViewModel: DashboardViewModel,
    calculatorViewModel: CalculatorViewModel
) {
    val scope = rememberCoroutineScope()
    var targetCourseIndex by remember { mutableIntStateOf(-1) }

    NavHost(
        navController = navController,
        startDestination = Screen.Splash,
        modifier = Modifier.padding(padding)
    ) {
        composable<Screen.Splash> {
            SplashScreen {
                navController.navigate(Screen.Dashboard) {
                    popUpTo(Screen.Splash) { inclusive = true }
                }
            }
        }
        
        composable<Screen.Dashboard> {
            DashboardScreen(
                viewModel = dashboardViewModel,
                onNavigateToCalculate = { navController.navigate(Screen.GPACalculator) },
                onNavigateToCgpa = { navController.navigate(Screen.CGPACalculator) },
                onNavigateToRecords = { navController.navigate(Screen.AcademicRecords) },
                onNavigateToScale = { navController.navigate(Screen.GradeScale) }
            )
        }
        
        composable<Screen.CGPACalculator> {
            CGPACalculatorScreen(onBack = { navController.navigateUp() })
        }
        
        composable<Screen.GPACalculator> {
            GPACalculatorScreen(
                viewModel = calculatorViewModel,
                onCalculate = { gpa, credits, qp, grade ->
                    navController.navigate(Screen.Result(gpa, credits, qp, grade))
                },
                onNavigateToWeight = { index ->
                    targetCourseIndex = index
                    navController.navigate(Screen.WeightCalculator)
                },
                onBack = { navController.navigateUp() }
            )
        }

        composable<Screen.Result> { backStackEntry ->
            val res = backStackEntry.toRoute<Screen.Result>()
            ResultScreen(
                gpa = res.gpa,
                credits = res.credits,
                qualityPoints = res.qualityPoints,
                grade = res.grade,
                onSave = {
                    scope.launch {
                        val semester = Semester(
                            name = calculatorViewModel.uiState.value.semesterName,
                            gpa = res.gpa,
                            totalCredits = res.credits,
                            qualityPoints = res.qualityPoints
                        )
                        dashboardViewModel.saveSemester(semester, calculatorViewModel.uiState.value.courses)
                        navController.navigate(Screen.Dashboard) {
                            popUpTo(Screen.Dashboard) { inclusive = true }
                        }
                    }
                },
                onBack = { navController.navigateUp() },
                onShare = {
                    // Simple share logic
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, "GPA786 Result\nGPA: ${res.gpa}\nCredits: ${res.credits}\nGrade: ${res.grade}\nBuilt by M. Umar Jamal")
                    }
                    val chooser = Intent.createChooser(intent, "Share Result")
                    (navController.context as Activity).startActivity(chooser)
                }
            )
        }
        
        composable<Screen.AcademicRecords> {
            AcademicRecordsScreen(
                viewModel = dashboardViewModel,
                onBack = { navController.navigateUp() }
            )
        }
        
        composable<Screen.WeightCalculator> {
            WeightCalculatorScreen(
                onCalculate = { marks ->
                    if (targetCourseIndex != -1) {
                        val course = calculatorViewModel.uiState.value.courses[targetCourseIndex]
                        calculatorViewModel.updateCourse(targetCourseIndex, course.copy(marks = marks))
                    }
                    navController.navigateUp()
                },
                onBack = { navController.navigateUp() }
            )
        }
        
        composable<Screen.GradeScale> {
            GradeScaleScreen(onBack = { navController.navigateUp() })
        }
        
        composable<Screen.Settings> {
            SettingsScreen(onBack = { navController.navigateUp() })
        }
    }
}
