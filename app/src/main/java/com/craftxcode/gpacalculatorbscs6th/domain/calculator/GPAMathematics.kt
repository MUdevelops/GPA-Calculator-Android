package com.craftxcode.gpacalculatorbscs6th.domain.calculator

object GPAMathematics {
    fun calculateGPA(qualityPoints: Double, totalCredits: Double): Double {
        if (totalCredits == 0.0) return 0.0
        return qualityPoints / totalCredits
    }

    fun calculateNewCGPA(
        previousCGPA: Double,
        previousCredits: Double,
        currentGPA: Double,
        currentCredits: Double
    ): Double {
        val previousQP = previousCGPA * previousCredits
        val currentQP = currentGPA * currentCredits
        val totalQP = previousQP + currentQP
        val totalCredits = previousCredits + currentCredits
        if (totalCredits == 0.0) return 0.0
        return totalQP / totalCredits
    }

    fun formatValue(value: Double, precision: Int = 2): String {
        return "%.${precision}f".format(value)
    }
}
