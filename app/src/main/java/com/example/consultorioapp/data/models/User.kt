package com.example.consultorioapp.data.models

data class User (
    val name: String = "",
    val email: String = "",
    val especialidad: String = "",
    val password: String = "",
) {
    fun isValidLogin(): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

    fun isValidSignup() : Boolean {
        return name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
    }
}