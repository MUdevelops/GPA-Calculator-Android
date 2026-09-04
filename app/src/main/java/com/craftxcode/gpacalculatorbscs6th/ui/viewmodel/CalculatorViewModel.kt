package com.craftxcode.gpacalculatorbscs6th.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.craftxcode.gpacalculatorbscs6th.data.entity.Course
import com.craftxcode.gpacalculatorbscs6th.domain.calculator.GradeCalculator
import com.craftxcode.gpacalculatorbscs6th.domain.model.GradeScale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CalculatorUiState(
    val courses: List<Course> = listOf(Course()),
    val gradeScale: GradeScale = GradeCalculator.defaultScale,
    val semesterName: String = "New Semester"
)

class CalculatorViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState = _uiState.asStateFlow()

    fun addCourse() {
        _uiState.update { it.copy(courses = it.courses + Course()) }
    }

    fun removeCourse(index: Int) {
        _uiState.update { 
            val newList = it.courses.toMutableList()
            if (newList.size > 1) {
                newList.removeAt(index)
            }
            it.copy(courses = newList)
        }
    }

    fun updateCourse(index: Int, course: Course) {
        _uiState.update {
            val newList = it.courses.toMutableList()
            val gradeEntry = GradeCalculator.calculateGrade(course.marks, it.gradeScale)
            val updatedCourse = course.copy(
                grade = gradeEntry.grade,
                gradePoint = gradeEntry.gradePoint
            )
            newList[index] = updatedCourse
            it.copy(courses = newList)
        }
    }

    fun updateSemesterName(name: String) {
        _uiState.update { it.copy(semesterName = name) }
    }
    
    fun setGradeScale(scale: GradeScale) {
        _uiState.update { it.copy(gradeScale = scale) }
        // Update all courses with new scale
        _uiState.value.courses.forEachIndexed { index, course ->
            updateCourse(index, course)
        }
    }
}
