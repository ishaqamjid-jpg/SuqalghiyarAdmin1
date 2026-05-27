
package com.isaac.souqalghiyaradmin.presentation.login

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.isaac.souqalghiyaradmin.domain.repository.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val accessType: String = ""
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val adminRepository: AdminRepository, // حقن الـ Repository
    application: Application
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _rememberMe = MutableStateFlow(false)
    val rememberMe: StateFlow<Boolean> = _rememberMe.asStateFlow()

    fun onUsernameChange(name: String) { _username.value = name }
    fun onPasswordChange(pass: String) { _password.value = pass }
    fun onRememberMeChange(checked: Boolean) { _rememberMe.value = checked }

    fun login(onSuccess: (String) -> Unit) {
        val user = _username.value.trim()
        val pass = _password.value.trim()

        if (user.isEmpty() || pass.isEmpty()) {
            _uiState.value = _uiState.value.copy(error = "يرجى تعبئة جميع الحقول")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            // الاتصال بقاعدة البيانات عبر الـ Repository
            val accessType = adminRepository.loginAdmin(user, pass)

            if (accessType != null) {
                _uiState.value = _uiState.value.copy(isLoading = false, isSuccess = true, accessType = accessType)
                
                // حفظ الجلسة إذا كان تذكرني مفعلاً
                if (_rememberMe.value) {
                    saveSessionLocally(user, accessType)
                }
                
                onSuccess(accessType)
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, error = "اسم المستخدم أو كلمة المرور غير صحيحة")
            }
        }
    }

    private fun saveSessionLocally(username: String, access: String) {
        val sharedPref = getApplication<Application>().getSharedPreferences("admin_prefs", Context.MODE_PRIVATE)
        sharedPref.edit().apply {
            putBoolean("is_logged_in", true)
            putString("admin_username", username)
            putString("admin_access", access)
            apply()
        }
    }
}
