package com.craftxcode.gpacalculatorbscs6th.domain.calculator

data class Component(
    val name: String,
    val obtainedMarks: Double,
    val totalMarks: Double,
    val weightPercentage: Double
)

object WeightCalculator {
    fun calculateWeightedPercentage(components: List<Component>): Double {
        if (components.isEmpty()) return 0.0
        return components.sumOf { (it.obtainedMarks / it.totalMarks) * it.weightPercentage }
    }

    fun isWeightValid(components: List<Component>): Boolean {
        return components.sumOf { it.weightPercentage } == 100.0
    }
}
