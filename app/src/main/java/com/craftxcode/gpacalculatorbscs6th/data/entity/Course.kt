package com.craftxcode.gpacalculatorbscs6th.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val semesterId: Long = 0,
    val code: String = "",
    val name: String = "",
    val creditHours: Double = 0.0,
    val marks: Double = 0.0,
    val grade: String = "",
    val gradePoint: Double = 0.0
)
