package com.isaac.souqalghiyaradmin.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.isaac.souqalghiyaradmin.domain.model.UserEmp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor() : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val _users = MutableStateFlow<List<UserEmp>>(emptyList())
    val users: StateFlow<List<UserEmp>> = _users

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        db.collection("users_emp").addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                _users.value = snapshot.documents.map { doc ->
                    doc.toObject(UserEmp::class.java)?.copy(id = doc.id) ?: UserEmp()
                }
            }
        }
    }

    fun saveUser(id: String, name: String, username: String, password: String, access: String) {
        viewModelScope.launch {
            val userMap = mapOf(
                "name" to name,
                "username" to username,
                "password" to password,
                "access" to access
            )

            if (id.isEmpty()) {
                // إضافة مستخدم جديد (Auto-ID)
                db.collection("users_emp").add(userMap)
            } else {
                // تحديث مستخدم موجود
                db.collection("users_emp").document(id).set(userMap)
            }
        }
    }

    fun deleteUser(id: String) {
        viewModelScope.launch {
            db.collection("users_emp").document(id).delete()
        }
    }
}
