package com.isaac.souqalghiyaradmin.presentation.dashboard

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    // لاحقاً سنحقن الـ Repository هنا لجلب عدد الطلبات المعلقة
) : ViewModel() {

    // حالة مؤقتة لعدد الطلبات المعلقة (Pending Orders)
    private val _pendingOrdersCount = MutableStateFlow(3) // رقم تجريبي
    val pendingOrdersCount: StateFlow<Int> = _pendingOrdersCount.asStateFlow()

    // دالة لتسجيل الخروج
    fun logout(onLogoutSuccess: () -> Unit) {
        // سيتم إضافة كود مسح الـ SharedPreferences هنا
        onLogoutSuccess()
    }
}
