package com.example.namma_vastraself_employment.data.repository

import com.example.namma_vastraself_employment.domain.model.User
import com.example.namma_vastraself_employment.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override val currentUser: Flow<User?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser == null) {
                trySend(null)
            } else {
                // In a real app, we might want to fetch the user profile from Firestore here
                // but for simplicity, we'll just emit a basic user object or handle it in the ViewModel
                trySend(User(id = firebaseUser.uid, phone = firebaseUser.phoneNumber ?: ""))
            }
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    override suspend fun loginWithPhone(phone: String): Result<Unit> {
        // Firebase Phone Auth is complex to implement fully here as it requires activity context for callbacks.
        // Usually, this is handled in the UI layer or via a service that takes the activity.
        return Result.success(Unit)
    }

    override suspend fun verifyOtp(otp: String): Result<User> {
        // Placeholder for OTP verification
        return Result.failure(Exception("OTP Verification requires Activity context for Firebase"))
    }

    override suspend fun logout() {
        auth.signOut()
    }

    override suspend fun updateProfile(user: User): Result<Unit> {
        return try {
            firestore.collection("users").document(user.id).set(user).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCurrentUser(): User? {
        val uid = auth.currentUser?.uid ?: return null
        return try {
            firestore.collection("users").document(uid).get().await().toObject(User::class.java)
        } catch (e: Exception) {
            null
        }
    }
}
