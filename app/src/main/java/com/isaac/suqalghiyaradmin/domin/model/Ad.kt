package com.isaac.souqalghiyaradmin.domain.model

data class Ad(
    val id: String = "",
    val title: String = "",
    val description: String = "", // تأكد من إضافة هذا الحقل إذا كنت تستخدمه في الواجهة
    val image_url: String = "",
    val click_action_type: String = "",
    val target_url: String = "",
    val priority: Int = 0,
    val is_active: Boolean = false,
    val start_date: com.google.firebase.Timestamp? = null,
    val end_date: com.google.firebase.Timestamp? = null,
    val created_at: com.google.firebase.Timestamp? = null
)
