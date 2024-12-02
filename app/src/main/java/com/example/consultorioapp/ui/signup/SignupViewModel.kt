package com.example.consultorioapp.ui.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.consultorioapp.data.models.User
import com.example.consultorioapp.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupViewModel(
    private val authRepository: AuthRepository,
    private val firestore: FirebaseFirestore
) : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _especialidad = MutableLiveData<String>()
    val especialidad: LiveData<String> = _especialidad

    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean> = _navigateToHome

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onNameChange(newName: String) {
        _name.value = newName
    }

    fun onEspecialidadChange(newEspecialidad: String) {
        _especialidad.value = newEspecialidad
    }

    fun onNavigatedToHome() {
        _navigateToHome.value = false // Reseteamos el estado después de navegar
    }


    fun register() {
        CoroutineScope(Dispatchers.IO).launch {
            val user = User(
                name = _name.value.orEmpty(),
                email = _email.value.orEmpty(),
                especialidad = _especialidad.value.orEmpty(),
                password = _password.value.orEmpty()
            )

            val result = authRepository.signup(user)

            if (result.isSuccess) {
                _navigateToHome.postValue(true)
                val userId = result.getOrNull()?.uid
                if (userId != null) {
                    firestore.collection("usuarios").document(userId).set(
                        mapOf(
                            "id" to userId,
                            "name" to user.name,
                            "email" to user.email,
                            "especialidad" to user.especialidad
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

