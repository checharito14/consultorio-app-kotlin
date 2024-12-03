package com.example.consultorioapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.consultorioapp.data.repository.AuthRepository
import com.example.consultorioapp.ui.HomePortrait
import com.example.consultorioapp.ui.InitialScreen
import com.example.consultorioapp.ui.login.LoginScreen
import com.example.consultorioapp.ui.login.LoginViewModel
import com.example.consultorioapp.ui.signup.SignupScreen
import com.example.consultorioapp.ui.signup.SignupViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun NavGraph(navController: NavHostController, auth: FirebaseAuth) {

    NavHost(navController = navController, startDestination = "initial") {
        composable("initial") {
            InitialScreen(
                navigateToLogin = { navController.navigate("logIn") },
                navigateToSignUp = { navController.navigate("signUp") }
            )
        }
        composable("logIn") {
            val viewModel = LoginViewModel(AuthRepository(auth))
            LoginScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable("signUp") {
            val authRepository = AuthRepository(auth)
            val firestore = FirebaseFirestore.getInstance()

            // Crea el ViewModel directamente
            val viewModel = SignupViewModel(authRepository, firestore)

            // Pasa el ViewModel a la pantalla
            SignupScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable("home") {
            HomePortrait()
        }
    }
}