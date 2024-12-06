package com.example.consultorioapp.data.models

import java.util.Date

data class Paciente(
    val id: String = "",
    val nombre: String,
    val edad: Int,
    val fechaRegistro: Date,
    val fotoPerfil: String
)