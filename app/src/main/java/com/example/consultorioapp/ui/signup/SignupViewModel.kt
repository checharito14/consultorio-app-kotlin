package com.example.consultorioapp.ui.signup

import android.util.Log
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        _uiState.value = uiState.value.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = uiState.value.copy(password = newPassword)
    }

    fun onNameChange(newName: String) {
        _uiState.value = uiState.value.copy(name = newName)
    }

    fun onNavigatedToHome() {
        _uiState.value =
            uiState.value.copy(navigateToHome = false) // Reseteamos el estado después de navegar
    }


    fun register() {
        val user = User(
            name = _uiState.value.name,
            email = _uiState.value.email,
            password = _uiState.value.password
        )

        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            val result = authRepository.signup(user)

            if (result.isSuccess) {
                val userId = result.getOrNull()?.uid
                if (userId != null) {
                    firestore.collection("usuarios").document(userId).set(
                        mapOf(
                            "id" to userId,
                            "name" to user.name,
                            "email" to user.email,
                        )
                    ).addOnSuccessListener {
                        Log.d("Usario", "Creado")
                    }.addOnFailureListener {
                        Log.d("Usuario", "ERROR")
                    }
                }
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
    val error: String? = null,
    val navigateToHome: Boolean = false
)

