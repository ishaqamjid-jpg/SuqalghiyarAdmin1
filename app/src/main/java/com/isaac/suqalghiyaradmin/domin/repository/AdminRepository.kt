package com.isaac.souqalghiyaradmin.domain.repository

interface AdminRepository {
    // دالة تسجيل الدخول تبحث في Firebase وترجع إما الصلاحية (Access) أو null في حال الفشل
    suspend fun loginAdmin(username: String, password: String): String?
}