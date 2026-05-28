package com.isaac.souqalghiyaradmin.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.isaac.souqalghiyaradmin.domain.model.SparePartCategory
import com.isaac.souqalghiyaradmin.domain.model.QualityType
import com.isaac.souqalghiyaradmin.domain.repository.ConstantsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ConstantsRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) : ConstantsRepository {

    override fun getSparePartCategories() = callbackFlow {
        val listener = db.collection("spare_parts_categories").addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                trySend(snapshot.documents.map { 
                    it.toObject(SparePartCategory::class.java)?.copy(id = it.id) ?: SparePartCategory() 
                })
            }
        }
        awaitClose { listener.remove() }
    }

    override fun getQualityTypes() = callbackFlow {
        val listener = db.collection("quality_types").addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                trySend(snapshot.documents.map { 
                    it.toObject(QualityType::class.java)?.copy(id = it.id) ?: QualityType() 
                })
            }
        }
        awaitClose { listener.remove() }
    }
}
