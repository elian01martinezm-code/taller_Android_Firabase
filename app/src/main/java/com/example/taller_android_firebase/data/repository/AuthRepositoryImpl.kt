package com.example.taller_android_firebase.data.repository

import com.example.taller_android_firebase.domain.model.UserSession
import com.example.taller_android_firebase.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<UserSession> {
        return suspendCancellableCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = task.result?.user
                        if (firebaseUser != null) {
                            continuation.resume(Result.success(UserSession(firebaseUser.uid, firebaseUser.email)))
                        } else {
                            continuation.resume(Result.failure(Exception("Usuario nulo al iniciar sesión")))
                        }
                    } else {
                        continuation.resume(Result.failure(task.exception ?: Exception("Error desconocido al iniciar sesión")))
                    }
                }
        }
    }

    override suspend fun register(email: String, password: String): Result<UserSession> {
        return suspendCancellableCoroutine { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = task.result?.user
                        if (firebaseUser != null) {
                            continuation.resume(Result.success(UserSession(firebaseUser.uid, firebaseUser.email)))
                        } else {
                            continuation.resume(Result.failure(Exception("Usuario nulo al registrarse")))
                        }
                    } else {
                        continuation.resume(Result.failure(task.exception ?: Exception("Error desconocido al registrarse")))
                    }
                }
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUser(): UserSession? {
        val firebaseUser = firebaseAuth.currentUser
        return firebaseUser?.let {
            UserSession(it.uid, it.email)
        }
    }
}
