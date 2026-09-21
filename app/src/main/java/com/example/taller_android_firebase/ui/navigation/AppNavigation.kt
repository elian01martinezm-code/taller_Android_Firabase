package com.example.taller_android_firebase.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taller_android_firebase.ui.auth.AuthViewModel
import com.example.taller_android_firebase.ui.auth.LoginScreen
import com.example.taller_android_firebase.ui.auth.RegisterScreen
import com.example.taller_android_firebase.ui.tasks.DraftsScreen
import com.example.taller_android_firebase.ui.tasks.TaskListScreen
import com.example.taller_android_firebase.ui.tasks.TaskViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()

    // Determinamos el destino inicial basado en si hay sesión activa
    val startDestination = remember {
        if (authViewModel.isUserLoggedIn()) {
            Screen.TaskList.route
        } else {
            Screen.Login.route
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = authViewModel,
                onAuthSuccess = {
                    navController.navigate(Screen.TaskList.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                viewModel = authViewModel,
                onAuthSuccess = {
                    // No se usa tras el cambio a registro manual, pero se mantiene por firma
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.TaskList.route) {
            // Obtenemos un TaskViewModel fresco para esta pantalla cada vez que entramos
            val taskViewModel: TaskViewModel = hiltViewModel()
            TaskListScreen(
                viewModel = taskViewModel,
                onLogoutClick = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.TaskList.route) { inclusive = true }
                    }
                },
                onNavigateToDrafts = {
                    navController.navigate(Screen.Drafts.route)
                }
            )
        }

        composable(Screen.Drafts.route) {
            // Obtenemos el TaskViewModel
            val taskViewModel: TaskViewModel = hiltViewModel()
            DraftsScreen(
                viewModel = taskViewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
