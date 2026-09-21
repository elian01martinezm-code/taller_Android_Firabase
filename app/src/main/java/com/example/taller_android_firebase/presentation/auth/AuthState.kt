package com.example.taller_android_firebase.presentation.auth

import com.example.taller_android_firebase.domain.model.UserSession

sealed interface AuthState {
    object Idle : AuthState
    object Loading : AuthState
    data class Success(val userSession: UserSession) : AuthState
    data class Error(val message: String) : AuthState
    object RegisterSuccess : AuthState
}
