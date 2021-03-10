package com.promanage.app.ui.dashboard

import androidx.lifecycle.*
import com.promanage.app.db.Project
import com.promanage.app.service.ProjectsRepository
import kotlinx.coroutines.launch

class DashboardViewModel(private val repository: ProjectsRepository) : ViewModel() {

    val allProjects: LiveData<List<Project>> = repository.allProjects.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(project: Project) = viewModelScope.launch {
        repository.insert(project)
    }

    /**
     * Launching a new coroutine to update the data in a non-blocking way
     */
    fun update(project: Project) = viewModelScope.launch {
        repository.update(project)
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

}

class DashboardViewModelFactory(private val repository: ProjectsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}