package com.isaac.souqalghiyaradmin.domain.model

data class UserEmp(
    val Id: String = "",       // مطابق للـ Document ID في Firebase
    val username: String = "", // تم تعديلها لتطابق الصورة
    val password: String = "", // تم تعديلها لتطابق الصورة
    val Name: String = "",     // كما هو في الصورة
    val Access: String = "employ"
)
