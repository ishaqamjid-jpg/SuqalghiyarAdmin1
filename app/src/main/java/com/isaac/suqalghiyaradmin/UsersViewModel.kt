package com.hadahapp.inventory.presentation.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadahapp.inventory.data.repository.UserRepository
import com.hadahapp.inventory.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    // جلب المستخدمين كمراقب حي (Live Update)
    val users = userRepository.getAllUsers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun saveUser(userId: Int?, name: String, userName: String, pass: String, isAdmin: Boolean) {
        viewModelScope.launch {
            val user = User(
                userId = userId ?: 0, // 0 يعني جديد
                name = name,
                userName = userName,
                password = pass,
                employId = if (isAdmin) 0 else 1 // 0 للأدمن، 1 للموظف
            )
            userRepository.insertUser(user)
        }
    }

    fun deleteUser(userId: Int) {
        viewModelScope.launch {
            userRepository.deleteUserById(userId)
        }
    }
}
