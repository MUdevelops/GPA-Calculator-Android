package com.craftxcode.gpacalculatorbscs6th.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable data object Splash : Screen
    @Serializable data object Dashboard : Screen
    @Serializable data object GPACalculator : Screen
    @Serializable data object AcademicRecords : Screen
    @Serializable data object GradeScale : Screen
    @Serializable data object Settings : Screen
    @Serializable data object WeightCalculator : Screen
    @Serializable data object CGPACalculator : Screen
    @Serializable data class Result(
        val gpa: Double,
        val credits: Double,
        val qualityPoints: Double,
        val grade: String
    ) : Screen
}
