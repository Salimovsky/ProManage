package com.promanage.app.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectsDao {
    @Query("SELECT * FROM project_table ORDER BY projectId ASC")
    fun getProjects(): Flow<List<Project>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(project: Project)

    @Update
    suspend fun update(project: Project)

    @Query("DELETE FROM project_table where projectId = :projectId")
    suspend fun deleteProject(projectId: Double)

    @Query("DELETE FROM project_table")
    suspend fun deleteAll()
}