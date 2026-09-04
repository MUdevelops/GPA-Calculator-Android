package com.craftxcode.gpacalculatorbscs6th.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "semesters")
data class Semester(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String = "",
    val gpa: Double = 0.0,
    val totalCredits: Double = 0.0,
    val qualityPoints: Double = 0.0,
    val dateCreated: Long = System.currentTimeMillis()
)
