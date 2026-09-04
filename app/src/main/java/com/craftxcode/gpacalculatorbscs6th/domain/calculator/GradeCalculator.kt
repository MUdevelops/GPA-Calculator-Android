package com.craftxcode.gpacalculatorbscs6th.domain.calculator

import com.craftxcode.gpacalculatorbscs6th.domain.model.GradeEntry
import com.craftxcode.gpacalculatorbscs6th.domain.model.GradeScale

object GradeCalculator {
    val defaultScale = GradeScale(
        name = "Default",
        entries = listOf(
            GradeEntry("A+", 90.0, 4.0),
            GradeEntry("A", 85.0, 4.0),
            GradeEntry("A-", 80.0, 3.67),
            GradeEntry("B+", 75.0, 3.33),
            GradeEntry("B", 70.0, 3.0),
            GradeEntry("B-", 65.0, 2.67),
            GradeEntry("C+", 60.0, 2.33),
            GradeEntry("C", 55.0, 2.0),
            GradeEntry("C-", 50.0, 1.67),
            GradeEntry("D", 40.0, 1.0),
            GradeEntry("F", 0.0, 0.0)
        ).sortedByDescending { it.minPercentage }
    )

    fun calculateGrade(percentage: Double, scale: GradeScale = defaultScale): GradeEntry {
        return scale.entries.firstOrNull { percentage >= it.minPercentage } ?: scale.entries.last()
    }
}
