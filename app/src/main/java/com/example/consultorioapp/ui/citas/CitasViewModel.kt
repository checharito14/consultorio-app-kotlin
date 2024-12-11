package com.example.consultorioapp.ui.citas

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.consultorioapp.data.models.Cita
import com.example.consultorioapp.data.repository.CitaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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

    //Lista de urgentes
    private val _citasUrgentes = MutableStateFlow<List<Cita>>(emptyList())
    val citasUrgentes: StateFlow<List<Cita>> = _citasUrgentes

    private val _citasCounter = MutableStateFlow(Triple(0, 0, 0))
    val citasCounter: StateFlow<Triple<Int, Int, Int>> = _citasCounter

    fun resetFormState() {
        _citaFormState.value = Cita(
            id = "",
            pacienteId = "",
            nombrePaciente = "",
            fecha = null,
            hora = null,
            descripcion = ""
        )
    }

    fun fetchCitas() {
        viewModelScope.launch {
            val citasList = repository.getCitas(userId)

            if (citasList.size > 1) {
                val citasOrdenadas = citasList.sortedBy {
                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                    LocalDateTime.parse("${it.fecha} ${it.hora}", formatter)
                }
                _citas.value = citasOrdenadas
                _citasCanceladas.value = citasOrdenadas.filter { it.cancelada }
                _citasNoCanceladas.value = citasOrdenadas.filter { !it.cancelada }
                _citasUrgentes.value = citasOrdenadas.filter { it.urgente }
            } else {
                _citas.value = citasList
                _citasCanceladas.value = citasList.filter { it.cancelada }
                _citasNoCanceladas.value = citasList.filter { !it.cancelada }
                _citasUrgentes.value = citasList.filter { it.urgente }
            }


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
                nombrePaciente = state.nombrePaciente,
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

    fun saveUrgentCita() {
        val state = _citaFormState.value
        if (state.descripcion.isNotEmpty() && state.pacienteId.isNotEmpty()) {
            val currentDateTime = LocalDateTime.now()
            val formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val formatterTime = DateTimeFormatter.ofPattern("HH:mm")
            val nuevaCita = Cita(
                id = UUID.randomUUID().toString(),
                pacienteId = state.pacienteId,
                nombrePaciente = state.nombrePaciente,
                descripcion = state.descripcion,
                fecha = currentDateTime.format(formatterDate),
                hora = currentDateTime.format(formatterTime),
                urgente = true
            )
            viewModelScope.launch {
                repository.nuevaCita(userId, nuevaCita)
            }
        }
    }

    fun deleteCita(cita: Cita) {
        viewModelScope.launch {
            repository.deleteCita(userId, cita)
            fetchCitas()
        }
    }
}