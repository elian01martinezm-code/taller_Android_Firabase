package com.example.taller_android_firebase.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taller_android_firebase.domain.usecase.GetCurrentUserUseCase
import com.example.taller_android_firebase.domain.usecase.LoginUseCase
import com.example.taller_android_firebase.domain.usecase.LogoutUseCase
import com.example.taller_android_firebase.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        checkCurrentUser()
    }

    fun checkCurrentUser() {
        val user = getCurrentUserUseCase()
        if (user != null) {
            _authState.value = AuthState.Success(user)
        } else {
            _authState.value = AuthState.Idle
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = loginUseCase(email, password)
            result.fold(
                onSuccess = { userSession ->
                    _authState.value = AuthState.Success(userSession)
                },
                onFailure = { exception ->
                    _authState.value = AuthState.Error(exception.message ?: "Error al iniciar sesión")
                }
            )
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = registerUseCase(email, password)
            result.fold(
                onSuccess = {
                    // Forzamos el cierre de sesión tras el registro exitoso
                    logoutUseCase()
                    _authState.value = AuthState.RegisterSuccess
                },
                onFailure = { exception ->
                    _authState.value = AuthState.Error(exception.message ?: "Error al registrarse")
                }
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = logoutUseCase()
            result.fold(
                onSuccess = {
                    _authState.value = AuthState.Idle
                },
                onFailure = { exception ->
                    _authState.value = AuthState.Error(exception.message ?: "Error al cerrar sesión")
                }
            )
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}
