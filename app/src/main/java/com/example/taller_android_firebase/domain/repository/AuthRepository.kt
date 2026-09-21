package com.example.taller_android_firebase.domain.repository

import com.example.taller_android_firebase.domain.model.UserSession

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<UserSession>
    suspend fun register(email: String, password: String): Result<UserSession>
    suspend fun logout(): Result<Unit>
    fun getCurrentUser(): UserSession?
}
