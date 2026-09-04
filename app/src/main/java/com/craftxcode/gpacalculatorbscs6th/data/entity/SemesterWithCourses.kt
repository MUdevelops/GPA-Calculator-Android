package com.craftxcode.gpacalculatorbscs6th.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SemesterWithCourses(
    @Embedded val semester: Semester,
    @Relation(
        parentColumn = "id",
        entityColumn = "semesterId"
    )
    val courses: List<Course>
)
