package com.example.namma_vastraself_employment.data.repository

import android.net.Uri
import com.example.namma_vastraself_employment.domain.model.Saree
import com.example.namma_vastraself_employment.domain.repository.SareeRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SareeRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : SareeRepository {

    private val dummySarees = listOf(
        Saree(
            id = "s1",
            weaverId = "w1",
            weaverName = "Basavaraj",
            weaverPhone = "919876543210",
            weaverLocation = "Ilkal, Bagalkot",
            imageUrls = listOf("https://images.unsplash.com/photo-1617627143750-d86bc21e42bb?q=80&w=800&auto=format&fit=crop"),
            title = "Classic Maroon Ilkal",
            description = "Handwoven with Topi Teni pallu and pure silk borders.",
            weaveType = "Ilkal",
            material = "Cotton Silk",
            suggestedPrice = 4500.0
        ),
        Saree(
            id = "s2",
            weaverId = "w2",
            weaverName = "Mallikarjun",
            weaverPhone = "919876543210",
            weaverLocation = "Molakalmuru, Chitradurga",
            imageUrls = listOf("https://images.unsplash.com/photo-1583391733956-3750e0ff4e8b?q=80&w=800&auto=format&fit=crop"),
            title = "Royal Blue Molakalmuru",
            description = "Traditional Molakalmuru silk with intricate animal motifs on the border.",
            weaveType = "Molakalmuru",
            material = "Pure Silk",
            suggestedPrice = 12500.0
        ),
        Saree(
            id = "s3",
            weaverId = "w1",
            weaverName = "Basavaraj",
            weaverPhone = "919876543210",
            weaverLocation = "Ilkal, Bagalkot",
            imageUrls = listOf("https://images.unsplash.com/photo-1611601679655-7ca3ce40498b?q=80&w=800&auto=format&fit=crop"),
            title = "Mustard Gold Festive Saree",
            description = "Bright mustard yellow saree with contrasting green border.",
            weaveType = "Ilkal",
            material = "Cotton",
            suggestedPrice = 3200.0
        )
    )

    override fun getSarees(): Flow<List<Saree>> = callbackFlow {
        val subscription = firestore.collection("sarees")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null || snapshot.isEmpty) {
                    trySend(dummySarees)
                } else {
                    val sarees = snapshot.toObjects(Saree::class.java)
                    trySend(sarees)
                }
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun getSareeById(id: String): Saree? {
        return try {
            firestore.collection("sarees").document(id).get().await().toObject(Saree::class.java)
        } catch (e: Exception) {
            dummySarees.find { it.id == id }
        }
    }

    override suspend fun uploadSaree(saree: Saree, images: List<Uri>): Result<Unit> {
        return try {
            val imageUrls = mutableListOf<String>()
            for (uri in images) {
                val ref = storage.reference.child("sarees/${saree.id}/${uri.lastPathSegment}")
                ref.putFile(uri).await()
                val downloadUrl = ref.downloadUrl.await().toString()
                imageUrls.add(downloadUrl)
            }
            val finalSaree = saree.copy(imageUrls = imageUrls)
            firestore.collection("sarees").document(saree.id).set(finalSaree).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateSaree(saree: Saree): Result<Unit> {
        return try {
            firestore.collection("sarees").document(saree.id).set(saree).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteSaree(id: String): Result<Unit> {
        return try {
            firestore.collection("sarees").document(id).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun markAsSold(id: String): Result<Unit> {
        return try {
            firestore.collection("sarees").document(id).update("status", "SOLD").await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getWeaverSarees(weaverId: String): Flow<List<Saree>> = callbackFlow {
        val subscription = firestore.collection("sarees")
            .whereEqualTo("weaverId", weaverId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null || snapshot.isEmpty) {
                    trySend(dummySarees.filter { it.weaverId == weaverId })
                } else {
                    val sarees = snapshot.toObjects(Saree::class.java)
                    trySend(sarees)
                }
            }
        awaitClose { subscription.remove() }
    }
}
