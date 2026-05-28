package com.isaac.souqalghiyaradmin.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.isaac.souqalghiyaradmin.domain.model.Ad
import com.isaac.souqalghiyaradmin.domain.repository.AdsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AdsRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) : AdsRepository {

    override fun getAds() = callbackFlow {
        val listener = db.collection("ads").addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                val ads = snapshot.documents.map { doc ->
                    doc.toObject(Ad::class.java)?.copy(id = doc.id) ?: Ad()
                }
                trySend(ads)
            }
        }
        awaitClose { listener.remove() }
    }

    override suspend fun addAd(ad: Ad) {
        db.collection("ads").add(ad).await()
    }

    override suspend fun deleteAd(adId: String) {
        db.collection("ads").document(adId).delete().await()
    }
}
