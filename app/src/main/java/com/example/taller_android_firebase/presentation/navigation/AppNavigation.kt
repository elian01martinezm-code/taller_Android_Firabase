package com.example.taller_android_firebase.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taller_android_firebase.presentation.auth.AuthState
import com.example.taller_android_firebase.presentation.auth.AuthViewModel
import com.example.taller_android_firebase.presentation.auth.LoginScreen
import com.example.taller_android_firebase.presentation.auth.RegisterScreen
import com.example.taller_android_firebase.presentation.tasks.DraftsScreen
import com.example.taller_android_firebase.presentation.tasks.TaskListScreen
import com.example.taller_android_firebase.presentation.tasks.TaskViewModel

@Composable
fun AppNavigation(
    authViewModel: AuthViewModel,
    taskViewModel: TaskViewModel
) {
    val navController = rememberNavController()
    val authState by authViewModel.authState.collectAsState()

    // Determinamos el destino inicial basado en si hay sesión
    val startDestination = if (authState is AuthState.Success) {
        Screen.TaskList.route
    } else {
        Screen.Login.route
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
                    navController.navigate(Screen.TaskList.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.TaskList.route) {
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
            DraftsScreen(
                viewModel = taskViewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
