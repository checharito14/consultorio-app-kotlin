package com.example.consultorioapp.data.repository

import android.util.Log
import com.example.consultorioapp.data.models.Paciente
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PacientesRepository(private val firestore: FirebaseFirestore) {

    suspend fun getPacientes(userId: String): List<Paciente> {
        return try {
            firestore.collection("usuarios").document(userId).collection("pacientes").get()
                .await().toObjects(Paciente::class.java)
        } catch (e: Exception) {
            Log.e("PacientesRepository", "Error al obtener pacientes: ${e.message}", e)
            emptyList() // Retorna una lista vacía si ocurre un error
        }
    }

    suspend fun addPaciente(userId: String, paciente: Paciente) {
        firestore.collection("usuarios").document(userId).collection("pacientes")
            .document(paciente.id).set(paciente).await()
    }

    suspend fun updatePaciente(userId: String, paciente: Paciente) {
        firestore.collection("usuarios").document(userId).collection("pacientes")
            .document(paciente.id).set(paciente).await()
    }

    suspend fun deletePaciente(userId: String, pacienteId: Paciente) {
        firestore.collection("usuarios").document(userId).collection("pacientes")
            .document(pacienteId.id).delete().await()
    }
}