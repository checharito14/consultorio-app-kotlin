package com.example.consultorioapp.ui.pacientes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.consultorioapp.data.models.Paciente
import com.example.consultorioapp.data.repository.PacientesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PacientesViewModel @Inject constructor(private val repository: PacientesRepository) : ViewModel() {
    private val _pacientes = MutableStateFlow<List<Paciente>>(emptyList())
    val pacientes: StateFlow<List<Paciente>> = _pacientes

    fun fetchPacientes(userId: String) {
        viewModelScope.launch {
            val pacientesList = repository.getPacientes(userId)
            Log.d("pacientes", "$pacientesList")
            _pacientes.value = pacientesList
        }
    }

    fun addPaciente(userId: String, paciente : Paciente) {
        viewModelScope.launch {
           repository.addPaciente(userId, paciente)
            fetchPacientes(userId)
        }
    }

    fun updatePaciente(userId: String, paciente: Paciente) {
        viewModelScope.launch {
            repository.updatePaciente(userId, paciente)
            fetchPacientes(userId)
        }
    }

    fun deletePaciente(userId: String, paciente: Paciente) {
        viewModelScope.launch {
            repository.deletePaciente(userId, paciente)
            fetchPacientes(userId)
        }
    }

}