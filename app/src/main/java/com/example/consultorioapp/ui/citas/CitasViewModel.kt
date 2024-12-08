package com.example.consultorioapp.ui.citas

import androidx.lifecycle.ViewModel
import com.example.consultorioapp.data.repository.PacientesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CitasViewModel @Inject constructor(private val userId: String, private val repository: CitaRepository) : ViewModel() {

}