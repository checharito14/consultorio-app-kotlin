package com.example.consultorioapp.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class HomeRepository(private val firestore: FirebaseFirestore) {
    suspend fun getName(userId: String): String {
        return try {
            val documentSnapshot = firestore.collection("usuarios").document(userId).get().await()
            if (documentSnapshot.exists()) {
                val name = documentSnapshot.getString("name")
                name ?: "Nombre no disponible"
            } else {
                "Usuario no encontrado"
            }
        } catch (e: Exception) {
            Log.e("Authrepository", "Error al obtener el usuario: ${e.message}", e)
            "Error"
        }
    }
}