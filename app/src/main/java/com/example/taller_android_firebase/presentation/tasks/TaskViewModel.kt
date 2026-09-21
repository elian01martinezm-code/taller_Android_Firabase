package com.example.taller_android_firebase.presentation.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taller_android_firebase.domain.model.Task
import com.example.taller_android_firebase.domain.model.TaskDraft
import com.example.taller_android_firebase.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getRemoteTasksUseCase: GetRemoteTasksUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getLocalDraftsUseCase: GetLocalDraftsUseCase,
    private val saveDraftUseCase: SaveDraftUseCase,
    private val deleteDraftUseCase: DeleteDraftUseCase,
    private val publishDraftUseCase: PublishDraftUseCase,
    private val updateDraftUseCase: UpdateDraftUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<TaskUiState>(TaskUiState.Loading)
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    private val _draftsState = MutableStateFlow<List<TaskDraft>>(emptyList())
    val draftsState: StateFlow<List<TaskDraft>> = _draftsState.asStateFlow()

    private val _publishState = MutableStateFlow<OperationState>(OperationState.Idle)
    val publishState: StateFlow<OperationState> = _publishState.asStateFlow()

    init {
        loadTasks()
        loadDrafts()
    }

    fun loadTasks() {
        viewModelScope.launch {
            _uiState.value = TaskUiState.Loading
            getRemoteTasksUseCase()
                .catch { e ->
                    _uiState.value = TaskUiState.Error(e.message ?: "Error al cargar tareas")
                }
                .collect { tasks ->
                    if (tasks.isEmpty()) {
                        _uiState.value = TaskUiState.Empty
                    } else {
                        _uiState.value = TaskUiState.Success(tasks)
                    }
                }
        }
    }

    private fun loadDrafts() {
        viewModelScope.launch {
            getLocalDraftsUseCase()
                .catch { _draftsState.value = emptyList() }
                .collect { drafts ->
                    _draftsState.value = drafts
                }
        }
    }

    fun createTask(title: String, description: String) {
        viewModelScope.launch {
            createTaskUseCase(title, description)
                .onFailure { e -> }
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            updateTaskUseCase(task)
        }
    }

    fun createDraft(title: String, description: String) {
        viewModelScope.launch {
            saveDraftUseCase(title, description)
        }
    }

    fun updateDraft(draft: TaskDraft) {
        viewModelScope.launch {
            updateDraftUseCase(draft)
        }
    }

    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            updateTaskUseCase(task.copy(isCompleted = !task.isCompleted))
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            deleteTaskUseCase(taskId)
        }
    }

    fun deleteDraft(draftId: String) {
        viewModelScope.launch {
            deleteDraftUseCase(draftId)
        }
    }

    fun publishDraft(draft: TaskDraft) {
        viewModelScope.launch {
            _publishState.value = OperationState.Loading
            publishDraftUseCase(draft)
                .onSuccess {
                    _publishState.value = OperationState.Success
                }
                .onFailure { e ->
                    _publishState.value = OperationState.Error(e.message ?: "Error al publicar")
                }
        }
    }

    fun resetPublishState() {
        _publishState.value = OperationState.Idle
    }
}
