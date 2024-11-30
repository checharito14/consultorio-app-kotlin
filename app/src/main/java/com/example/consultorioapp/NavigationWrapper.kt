package com.example.consultorioapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.consultorioapp.presentation.initial.InitialScreen
import com.example.consultorioapp.presentation.login.LoginScreen
import com.example.consultorioapp.presentation.signup.SignupScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavigationWrapper(navHostController: NavHostController, auth: FirebaseAuth) {

    NavHost(navController = navHostController, startDestination = "initial") {
        composable("initial") {
            InitialScreen(
                navigateToLogin = { navHostController.navigate("logIn")},
                navigateToSignUp = { navHostController.navigate("signUp")}
            )
        }
        composable("logIn") {
            LoginScreen(auth)
        }
        composable("signUp") {
            SignupScreen(auth)
        }
    }
}