package com.craftxcode.gpacalculatorbscs6th.data.dao

import androidx.room.*
import com.craftxcode.gpacalculatorbscs6th.data.entity.Course
import com.craftxcode.gpacalculatorbscs6th.data.entity.Semester
import com.craftxcode.gpacalculatorbscs6th.data.entity.SemesterWithCourses
import kotlinx.coroutines.flow.Flow

@Dao
interface SemesterDao {
    @Transaction
    @Query("SELECT * FROM semesters ORDER BY dateCreated DESC")
    fun getAllSemesters(): Flow<List<SemesterWithCourses>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSemester(semester: Semester): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<Course>)

    @Delete
    suspend fun deleteSemester(semester: Semester)

    @Query("DELETE FROM courses WHERE semesterId = :semesterId")
    suspend fun deleteCoursesForSemester(semesterId: Long)

    @Transaction
    suspend fun saveSemesterWithCourses(semester: Semester, courses: List<Course>) {
        val id = insertSemester(semester)
        val coursesWithId = courses.map { it.copy(semesterId = id) }
        insertCourses(coursesWithId)
    }
}
