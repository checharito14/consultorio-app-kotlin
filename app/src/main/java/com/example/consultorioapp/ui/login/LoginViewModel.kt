package com.example.consultorioapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.consultorioapp.ValidationUtils
import com.example.consultorioapp.data.models.User
import com.example.consultorioapp.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _uiState.value = uiState.value.copy(
            email = newEmail,
            emailError = if (ValidationUtils.isEmailValid(newEmail)) null else "Ingresa un correo valido"
        )


    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = uiState.value.copy(
            password = newPassword,
            passwordError = if (ValidationUtils.isPasswordValid(newPassword)) null else "La contraseña debe contener al menos 6 caracteres"
        )

    }

    //LOGIN
    fun login() {
        val user = User(email = _uiState.value.email, password = _uiState.value.password)

        if (!ValidationUtils.isEmailValid(_uiState.value.email)) {
            _uiState.value = _uiState.value.copy(emailError = "Ingresa un correo valido")
            return
        }
        if (!ValidationUtils.isPasswordValid(uiState.value.password)) {
            _uiState.value =
                _uiState.value.copy(passwordError = "La contraseña debe contener al menos 6 caracteres")
            return
        }
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            passwordError = null,
            emailError = null,
            error = null
        )

        viewModelScope.launch {
            val result = repository.login(user)

            if (result.isSuccess) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    success = true,
                    navigateToHome = true
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Usuario no encontrado",
                    password = "",
                )
            }
        }
    }

    fun onNavigatedToHome() {
        _uiState.value =
            uiState.value.copy(navigateToHome = false) // Reseteamos el estado después de navegar
    }
}

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val navigateToHome: Boolean = false
)
