package com.example.consultorioapp.data.models

import android.os.Parcelable
import java.util.Date

data class Paciente(
    val id: String = "",
    val nombre: String = "",
    val edad: Int = 0,
    val fechaRegistro: String = "",
)