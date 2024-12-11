package com.example.consultorioapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.consultorioapp.data.models.Paciente
import com.example.consultorioapp.data.repository.AuthRepository
import com.example.consultorioapp.data.repository.HomeRepository
import com.example.consultorioapp.data.repository.PacientesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userId: String,
    private val homeRepository: HomeRepository
) : ViewModel() {
    private val _usuario = MutableStateFlow("")
    val usuario: StateFlow<String> = _usuario

    fun getUserName() {
        viewModelScope.launch {
            val userName = homeRepository.getName(userId)
            _usuario.value = userName
        }
    }
}