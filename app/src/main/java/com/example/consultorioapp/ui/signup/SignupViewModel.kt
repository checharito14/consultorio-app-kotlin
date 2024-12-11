package com.example.consultorioapp.ui.signup

import android.util.Log
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.consultorioapp.ValidationUtils
import com.example.consultorioapp.data.models.User
import com.example.consultorioapp.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignupViewModel(
    private val authRepository: AuthRepository,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupState())
    val uiState: StateFlow<SignupState> = _uiState.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _uiState.value = uiState.value.copy(
            email = newEmail,
            emailError = if (ValidationUtils.isEmailValid(newEmail)) null else "Ingresa un correo válido *"
        )
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = uiState.value.copy(
            password = newPassword,
            passwordError = if (ValidationUtils.isEmailValid(newPassword)) null else "La contraseña debe contener al menos 6 caracteres *"
        )
    }

    fun onNameChange(newName: String) {
        _uiState.value = uiState.value.copy(
            name = newName,
            nameError = if (ValidationUtils.isNotEmpty(newName)) null else "Ingresa un nombre válido *"
        )
    }

    fun onNavigatedToHome() {
        _uiState.value =
            uiState.value.copy(navigateToHome = false) // Reseteamos el estado después de navegar
    }


    fun register() {
        if (!ValidationUtils.isNotEmpty(uiState.value.name)) {
            _uiState.value = _uiState.value.copy(nameError = "Ingresa tu nombre *")
            return
        }
        if (!ValidationUtils.isEmailValid(_uiState.value.email)) {
            _uiState.value = _uiState.value.copy(emailError = "Ingresa un correo válido *")
            return
        }
        if (!ValidationUtils.isPasswordValid(uiState.value.password)) {
            _uiState.value =
                _uiState.value.copy(passwordError = "La contraseña debe contener al menos 6 caracteres *")
            return
        }

        val user = User(
            name = _uiState.value.name,
            email = _uiState.value.email,
            password = _uiState.value.password
        )

        _uiState.value = _uiState.value.copy(
            isLoading = true, passwordError = null,
            emailError = null, nameError = null
        )

        viewModelScope.launch {
            val result = authRepository.signup(user)

            if (result.isSuccess) {
                val userId = result.getOrNull()?.uid
                if (userId != null) {
                    firestore.collection("usuarios").document(userId).set(
                        mapOf(
                            "id" to userId,
                            "name" to user.name.split(" ").joinToString(" ") { palabra ->
                                palabra.lowercase().replaceFirstChar { it.uppercase() }
                            },
                            "email" to user.email.trim(),
                        )
                    ).addOnSuccessListener {
                        Log.d("Usario", "Creado")
                    }.addOnFailureListener {
                        Log.d("Usuario", "ERROR")
                    }
                }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    success = true,
                    navigateToHome = true
                )
            }
        }
    }
}


data class SignupState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val nameError: String? = null,
    val navigateToHome: Boolean = false
)

