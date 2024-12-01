package com.example.consultorioapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.consultorioapp.data.repository.AuthRepository
import com.example.consultorioapp.ui.Home
import com.example.consultorioapp.ui.InitialScreen
import com.example.consultorioapp.ui.login.LoginScreen
import com.example.consultorioapp.ui.login.LoginViewModel
import com.example.consultorioapp.ui.signup.SignupScreen
import com.google.firebase.auth.FirebaseAuth

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
                auth,
                viewModel = viewModel
            )
        }
        composable("signUp") {
            SignupScreen(auth)
        }
        composable("home") {
            Home()
        }
    }
}