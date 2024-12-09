package com.example.consultorioapp.data.repository

import com.example.consultorioapp.data.models.Cita
import com.example.consultorioapp.data.models.Paciente
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CitaRepository(private val firestore: FirebaseFirestore) {

    suspend fun nuevaCita(userId: String, cita: Cita) {
        firestore.collection("usuarios").document(userId).collection("citas")
            .document(cita.id).set(cita).await()
    }

    suspend fun getCitas(userId: String): List<Cita> {
        return try {
            firestore.collection("usuarios").document(userId).collection("citas").get().await()
                .toObjects(Cita::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun obtenerTotalCitas(userId: String): Pair<Int, Int> {
        val citasRef = firestore.collection("usuarios")
            .document(userId)
            .collection("citas")

        val (canceladasTask, noCanceladasTask) = listOf(
            citasRef.whereEqualTo("cancelada", true).get(),
            citasRef.whereEqualTo("cancelada", false).get()
        ).map { it.await() }

        return Pair(canceladasTask.size(), noCanceladasTask.size())
    }

    suspend fun cambiarEstadoCita(userId: String, cita: Cita) {
        val citaRef = firestore.collection("usuarios").document(userId).collection("citas").document(cita.id)

        citaRef.update("cancelada", true)
    }
}