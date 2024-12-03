package com.example.consultorioapp.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.example.consultorioapp.data.models.User
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRepository(private val auth: FirebaseAuth) {

    // Función de login
    suspend fun login(user: User): Result<Boolean> {
        if(!user.isValidLogin()) {
            return Result.failure(IllegalArgumentException("Correo o contraseña invalidos"))
        }
        return try {
            // Intentar iniciar sesión con correo y contraseña
            auth.signInWithEmailAndPassword(user.email, user.password).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signup(user: User): Result<FirebaseUser?> {
        if(user.isValidSignup()) {
            return Result.failure(IllegalArgumentException("Todos los campos son requeridos"))
        }
        return try {
            val result = auth.createUserWithEmailAndPassword(user.email, user.password).await()
            Result.success(result.user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
