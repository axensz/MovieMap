package com.upbapps.moviemap.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object FirebaseManager {
    private val auth: FirebaseAuth = Firebase.auth

    fun testConnection(onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (auth.app != null) {
            onSuccess()
        } else {
            onError("Error al inicializar Firebase")
        }
    }
} 