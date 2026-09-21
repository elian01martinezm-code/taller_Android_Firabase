package com.example.taller_android_firebase.presentation.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taller_android_firebase.domain.model.TaskDraft

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DraftsScreen(
    viewModel: TaskViewModel,
    onBackClick: () -> Unit
) {
    val drafts by viewModel.draftsState.collectAsState()
    val publishState by viewModel.publishState.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var draftToEdit by remember { mutableStateOf<TaskDraft?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(publishState) {
        when (val state = publishState) {
            is OperationState.Success -> {
                snackbarHostState.showSnackbar("¡Tarea publicada con éxito!")
                viewModel.resetPublishState()
            }
            is OperationState.Error -> {
                snackbarHostState.showSnackbar("Error: ${state.message}")
                viewModel.resetPublishState()
            }
            else -> {}
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Borradores Locales (Offline)") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Nuevo Borrador")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (drafts.isEmpty()) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("No tienes borradores locales", style = MaterialTheme.typography.bodyLarge)
                    Text("Los borradores se guardan en Room", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(drafts, key = { it.id }) { draft ->
                        DraftItem(
                            draft = draft,
                            publishLoading = publishState is OperationState.Loading,
                            onPublishClick = { viewModel.publishDraft(draft) },
                            onDeleteClick = { viewModel.deleteDraft(draft.id) },
                            onEditClick = { draftToEdit = draft }
                        )
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        DraftDialog(
            title = "Nuevo Borrador",
            onDismiss = { showAddDialog = false },
            onConfirm = { title, desc ->
                viewModel.createDraft(title, desc)
                showAddDialog = false
            }
        )
    }

    draftToEdit?.let { draft ->
        DraftDialog(
            title = "Editar Borrador",
            initialTitle = draft.title,
            initialDescription = draft.description,
            onDismiss = { draftToEdit = null },
            onConfirm = { title, desc ->
                viewModel.updateDraft(draft.copy(title = title, description = desc))
                draftToEdit = null
            }
        )
    }
}

@Composable
fun DraftItem(
    draft: TaskDraft,
    publishLoading: Boolean,
    onPublishClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1.0f)
                    .padding(end = 8.dp)
            ) {
                Text(text = draft.title, style = MaterialTheme.typography.titleMedium)
                if (draft.description.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = draft.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Local (Room)", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
            }
            
            IconButton(onClick = onPublishClick, enabled = !publishLoading) {
                if (publishLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                } else {
                    Icon(imageVector = Icons.Default.CloudUpload, contentDescription = "Publicar", tint = MaterialTheme.colorScheme.primary)
                }
            }

            IconButton(onClick = onEditClick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.secondary)
            }

            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Borrar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun DraftDialog(
    title: String,
    initialTitle: String = "",
    initialDescription: String = "",
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var titleInput by remember { mutableStateOf(initialTitle) }
    var descriptionInput by remember { mutableStateOf(initialDescription) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column {
                OutlinedTextField(
                    value = titleInput,
                    onValueChange = { titleInput = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = descriptionInput,
                    onValueChange = { descriptionInput = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { if (titleInput.isNotBlank()) onConfirm(titleInput.trim(), descriptionInput.trim()) },
                enabled = titleInput.isNotBlank()
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
