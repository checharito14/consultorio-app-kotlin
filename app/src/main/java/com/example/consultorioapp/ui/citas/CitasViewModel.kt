package com.example.consultorioapp.ui.citas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.consultorioapp.data.models.Cita
import com.example.consultorioapp.data.repository.CitaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CitasViewModel @Inject constructor(
    private val userId: String,
    private val repository: CitaRepository
) : ViewModel() {
    private val _citaFormState = MutableStateFlow(Cita())
    val citaFormState: StateFlow<Cita> = _citaFormState

    private val _citas = MutableStateFlow<List<Cita>>(emptyList())
    val citas: StateFlow<List<Cita>> = _citas

    //Lista de canceladas
    private val _citasCanceladas = MutableStateFlow<List<Cita>>(emptyList())
    val citasCanceladas: StateFlow<List<Cita>> = _citasCanceladas

    //Lista de no canceladas
    private val _citasNoCanceladas = MutableStateFlow<List<Cita>>(emptyList())
    val citasNoCanceladas: StateFlow<List<Cita>> = _citasNoCanceladas

    private val _citasCounter = MutableStateFlow(Triple(0, 0, 0))
    val citasCounter: StateFlow<Triple<Int, Int, Int>> = _citasCounter

    fun fetchCitas() {
        viewModelScope.launch {
            val citasList = repository.getCitas(userId)

            _citas.value = citasList
            _citasCanceladas.value = citasList.filter { it.cancelada }
            _citasNoCanceladas.value = citasList.filter { !it.cancelada }
        }
    }

    fun contadorCitas() {
        viewModelScope.launch {
            val (canceladas, noCanceladas) = repository.obtenerTotalCitas(userId)
            val total = canceladas + noCanceladas
            _citasCounter.value = Triple(canceladas, noCanceladas, total)
        }
    }

    //Formulario nueva cita
    fun updateFormState(newState: Cita) {
        _citaFormState.value = newState
    }

    fun saveCita() {
        val state = _citaFormState.value
        if (state.descripcion.isNotEmpty() && state.pacienteId.isNotEmpty() && state.hora != null && state.fecha != null) {
            val nuevaCita = Cita(
                id = UUID.randomUUID().toString(),
                pacienteId = state.pacienteId,
                descripcion = state.descripcion,
                fecha = state.fecha!!,
                hora = state.hora!!
            )
            viewModelScope.launch {
                repository.nuevaCita(userId, nuevaCita)
            }
        }
    }

    fun changeCitaState(cita: Cita) {
        viewModelScope.launch {
            repository.cambiarEstadoCita(userId, cita)
        }
    }
}