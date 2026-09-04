package com.craftxcode.gpacalculatorbscs6th.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class GradeScale(
    val name: String,
    val entries: List<GradeEntry>
)

@Serializable
data class GradeEntry(
    val grade: String,
    val minPercentage: Double,
    val gradePoint: Double
)
