
package com.isaac.souqalghiyaradmin.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.isaac.souqalghiyaradmin.domain.repository.AdminRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor() : AdminRepository {
    
    private val db = FirebaseFirestore.getInstance()

    override suspend fun loginAdmin(username: String, password: String): String? {
        return try {
            // البحث في الكولكشن الذي بنيته في Firebase
            val snapshot = db.collection("users_emp")
                .whereEqualTo("Usrename", username)
                .whereEqualTo("Password", password)
                .get()
                .await() // ننتظر الرد من الإنترنت
            
            if (!snapshot.isEmpty) {
                // إذا وجد الموظف، نرجع صلاحيته (Access)
                val document = snapshot.documents[0]
                document.getString("Access") // إما Admin أو Employ
            } else {
                null // اسم المستخدم أو كلمة المرور خطأ
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null // حدث خطأ في الاتصال بالإنترنت
        }
    }
}
