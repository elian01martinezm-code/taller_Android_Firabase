package com.example.taller_android_firebase.data.repository

import com.example.taller_android_firebase.data.mapper.toDomain
import com.example.taller_android_firebase.data.remote.model.TaskDocument
import com.example.taller_android_firebase.domain.model.Task
import com.example.taller_android_firebase.domain.repository.TaskRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : TaskRepository {

    private val tasksCollection = firestore.collection("tasks")

    override fun getTasksRealTime(): Flow<List<Task>> {
        val uid = firebaseAuth.currentUser?.uid
        println("DEBUG: Consultando tareas para el usuario UID: $uid")
        if (uid == null) {
            return flow { emit(emptyList()) }
        }

        return callbackFlow {
            val listenerRegistration = tasksCollection
                .whereEqualTo("ownerId", uid)
                .orderBy("createdAt")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    if (snapshot != null) {
                        val tasks = snapshot.documents.mapNotNull { doc ->
                            val docData = doc.toObject(TaskDocument::class.java)
                            docData?.copy(id = doc.id)?.toDomain()
                        }
                        trySend(tasks)
                    }
                }

            awaitClose { listenerRegistration.remove() }
        }
    }

    override suspend fun createTask(title: String, description: String): Result<Unit> {
        val uid = firebaseAuth.currentUser?.uid ?: return Result.failure(Exception("Usuario no autenticado"))
        
        return suspendCancellableCoroutine { continuation ->
            val docRef = tasksCollection.document()
            val taskDoc = TaskDocument(
                id = docRef.id,
                title = title,
                description = description,
                taskCompleted = false,
                legacyCompleted = false,
                createdAt = System.currentTimeMillis(),
                ownerId = uid
            )

            docRef.set(taskDoc)
                .addOnSuccessListener {
                    continuation.resume(Result.success(Unit))
                }
                .addOnFailureListener { e ->
                    continuation.resume(Result.failure(e))
                }
        }
    }

    override suspend fun updateTask(task: Task): Result<Unit> {
        return suspendCancellableCoroutine { continuation ->
            tasksCollection.document(task.id)
                .update(
                    "title", task.title,
                    "description", task.description,
                    "isCompleted", task.isCompleted
                )
                .addOnSuccessListener {
                    continuation.resume(Result.success(Unit))
                }
                .addOnFailureListener { e ->
                    continuation.resume(Result.failure(e))
                }
        }
    }

    override suspend fun deleteTask(taskId: String): Result<Unit> {
        return suspendCancellableCoroutine { continuation ->
            tasksCollection.document(taskId)
                .delete()
                .addOnSuccessListener {
                    continuation.resume(Result.success(Unit))
                }
                .addOnFailureListener { e ->
                    continuation.resume(Result.failure(e))
                }
        }
    }
}
