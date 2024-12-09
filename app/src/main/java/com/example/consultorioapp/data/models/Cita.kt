package com.example.consultorioapp.data.models

data class Cita(
    val id: String = "",
    val pacienteId: String = "",
    val fecha: String? = null,
    val hora: String? = null,
    val descripcion: String = "",
    val cancelada: Boolean = false

)
