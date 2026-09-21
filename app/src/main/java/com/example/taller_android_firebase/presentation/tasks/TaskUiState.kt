package com.example.taller_android_firebase.presentation.tasks

import com.example.taller_android_firebase.domain.model.Task

sealed interface TaskUiState {
    object Loading : TaskUiState
    data class Success(val tasks: List<Task>) : TaskUiState
    object Empty : TaskUiState
    data class Error(val message: String) : TaskUiState
}
