package com.example.taller_android_firebase.domain.usecase

import com.example.taller_android_firebase.domain.model.UserSession
import com.example.taller_android_firebase.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): UserSession? {
        return authRepository.getCurrentUser()
    }
}
