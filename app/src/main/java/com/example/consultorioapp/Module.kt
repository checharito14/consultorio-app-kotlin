package com.example.consultorioapp

import com.example.consultorioapp.data.repository.PacientesRepository
import com.example.consultorioapp.ui.pacientes.PacientesViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun providePacientesRepository(firestore: FirebaseFirestore): PacientesRepository {
        return PacientesRepository(firestore)
    }

}