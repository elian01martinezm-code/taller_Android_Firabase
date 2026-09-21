package com.example.taller_android_firebase.presentation.tasks

sealed interface OperationState {
    object Idle : OperationState
    object Loading : OperationState
    object Success : OperationState
    data class Error(val message: String) : OperationState
}
