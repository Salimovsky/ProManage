package com.promanage.app.service

import androidx.annotation.WorkerThread
import com.promanage.app.db.Project
import com.promanage.app.db.ProjectsDao
import kotlinx.coroutines.flow.Flow

class ProjectsRepository(private val projectsDao: ProjectsDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allProjects: Flow<List<Project>> = projectsDao.getProjects()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(project: Project) {
        projectsDao.insert(project)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(project: Project){
        projectsDao.update(project)
    }
}