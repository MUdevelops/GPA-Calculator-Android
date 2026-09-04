package com.craftxcode.gpacalculatorbscs6th.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craftxcode.gpacalculatorbscs6th.data.dao.SemesterDao
import com.craftxcode.gpacalculatorbscs6th.data.entity.Course
import com.craftxcode.gpacalculatorbscs6th.data.entity.Semester
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class DashboardStats(
    val currentGPA: Double = 0.0,
    val overallCGPA: Double = 0.0,
    val totalCredits: Double = 0.0
)

class DashboardViewModel(private val semesterDao: SemesterDao) : ViewModel() {
    val semesters = semesterDao.getAllSemesters()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val stats = semesters.map { list ->
        val totalCredits = list.sumOf { it.semester.totalCredits }
        val totalQP = list.sumOf { it.semester.qualityPoints }
        val cgpa = if (totalCredits > 0) totalQP / totalCredits else 0.0
        
        DashboardStats(
            currentGPA = list.firstOrNull()?.semester?.gpa ?: 0.0,
            overallCGPA = cgpa,
            totalCredits = totalCredits
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DashboardStats())

    suspend fun saveSemester(semester: Semester, courses: List<Course>) {
        semesterDao.saveSemesterWithCourses(semester, courses)
    }
}
