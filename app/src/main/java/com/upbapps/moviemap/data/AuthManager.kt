package com.upbapps.moviemap.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object AuthManager {
    private const val TAG = "AuthManager"
    private val auth: FirebaseAuth = Firebase.auth

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    fun signIn(email: String, password: String, onSuccess: (FirebaseUser) -> Unit, onError: (String) -> Unit) {
        try {
            Log.d(TAG, "Intentando iniciar sesión con email: $email")
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        auth.currentUser?.let { user ->
                            onSuccess(user)
                        }
                    } else {
                        val errorMessage = when {
                            task.exception?.message?.contains("no user record") == true -> 
                                "No existe una cuenta con este correo"
                            task.exception?.message?.contains("password is invalid") == true -> 
                                "Contraseña incorrecta"
                            else -> task.exception?.message ?: "Error de autenticación"
                        }
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        onError(errorMessage)
                    }
                }
        } catch (e: Exception) {
            Log.e(TAG, "Error inesperado en signIn", e)
            onError("Error inesperado: ${e.message}")
        }
    }

    fun register(email: String, password: String, username: String, onSuccess: (FirebaseUser) -> Unit, onError: (String) -> Unit) {
        try {
            Log.d(TAG, "Intentando registrar usuario con email: $email")
            if (password.length < 6) {
                onError("La contraseña debe tener al menos 6 caracteres")
                return
            }
            
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        // Actualizar el perfil con el nombre de usuario
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(username)
                            .build()

                        auth.currentUser?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { profileTask ->
                                if (profileTask.isSuccessful) {
                                    auth.currentUser?.let { user ->
                                        onSuccess(user)
                                    }
                                } else {
                                    onError("Error al actualizar el perfil")
                                }
                            }
                    } else {
                        val errorMessage = when {
                            task.exception?.message?.contains("email address is already in use") == true -> 
                                "Este correo ya está registrado"
                            task.exception?.message?.contains("badly formatted") == true -> 
                                "Formato de correo inválido"
                            else -> task.exception?.message ?: "Error al crear la cuenta"
                        }
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        onError(errorMessage)
                    }
                }
        } catch (e: Exception) {
            Log.e(TAG, "Error inesperado en register", e)
            onError("Error inesperado: ${e.message}")
        }
    }

    fun signOut() {
        try {
            auth.signOut()
            Log.d(TAG, "Usuario cerró sesión exitosamente")
        } catch (e: Exception) {
            Log.e(TAG, "Error al cerrar sesión", e)
        }
    }

    fun getUsername(): String {
        return auth.currentUser?.displayName ?: "Usuario"
    }
} 