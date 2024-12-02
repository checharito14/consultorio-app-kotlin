package com.example.consultorioapp.ui.login

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.consultorioapp.data.models.User
import com.example.consultorioapp.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginState = MutableLiveData(LoginState())
    val loginState: LiveData<LoginState> = _loginState

    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean> = _navigateToHome

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onNavigatedToHome() {
        _navigateToHome.value = false // Reseteamos el estado después de navegar
    }

    fun login(auth: FirebaseAuth) {
        _loginState.value = LoginState(isLoading = true)
        CoroutineScope(Dispatchers.IO).launch {
            val user = User(email = _email.value.orEmpty(), password = _password.value.orEmpty())
            val result = repository.login(user)

            if (result.isSuccess) {
                _loginState.postValue(LoginState(success = true))
                _navigateToHome.postValue(true)  // Navegar al inicio si es exitoso
            } else {
                _loginState.postValue(LoginState(error = result.exceptionOrNull()?.message))
            }
        }
    }
}

data class LoginState(
    val success: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)