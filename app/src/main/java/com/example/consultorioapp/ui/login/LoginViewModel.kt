package com.example.consultorioapp.ui.login

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
            error = if (!ValidationUtils.isEmailValid(newEmail)) "Ingresa un correo valido" else null
        )


    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = uiState.value.copy(
            password = newPassword,
            error = if (!ValidationUtils.isPasswordValid(newPassword)) "La contraseña debe contener al menos 6 caracteres" else null
        )

    }

    //LOGIN
    fun login() {
        val state = _uiState.value ?: return
        if(!ValidationUtils.isEmailValid(state.email) || !ValidationUtils.isPasswordValid(state.password)) {
            _uiState.value = state.copy(error = "Error")
            return
        }
        _uiState.value = state.copy(isLoading = true, error = null)

        viewModelScope.launch {
            val user = User(email = _uiState.value.email, password = _uiState.value.password)
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
                    error = result.exceptionOrNull()?.message
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
    val navigateToHome: Boolean = false
)
