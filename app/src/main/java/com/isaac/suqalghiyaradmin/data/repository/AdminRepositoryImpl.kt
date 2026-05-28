package com.isaac.souqalghiyaradmin.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.isaac.souqalghiyaradmin.domain.repository.AdminRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor() : AdminRepository {
    
    private val db = FirebaseFirestore.getInstance()

    override suspend fun loginAdmin(username: String, password: String): String? {
        return try {
            // الآن نستخدم أسماء الحقول الصحيحة كما في Firebase
            val snapshot = db.collection("users_emp")
                .whereEqualTo("username", username) 
                .whereEqualTo("password", password)
                .get()
                .await() 
            
            if (!snapshot.isEmpty) {
                // نستخدم "access" لأن الحقل في Firebase الآن بحروف صغيرة
                val document = snapshot.documents[0]
                document.getString("access") 
            } else {
                null 
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
