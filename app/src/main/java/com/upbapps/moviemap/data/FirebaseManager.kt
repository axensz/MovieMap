package com.upbapps.moviemap.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object FirebaseManager {
    private const val TAG = "FirebaseManager"
    private val auth: FirebaseAuth = Firebase.auth

    fun testConnection(onSuccess: () -> Unit, onError: (String) -> Unit) {
        // Verificar si Firebase está inicializado correctamente
        if (auth.app != null) {
            Log.d(TAG, "Firebase inicializado correctamente")
            onSuccess()
        } else {
            Log.e(TAG, "Error al inicializar Firebase")
            onError("Error al inicializar Firebase")
        }
    }
} 