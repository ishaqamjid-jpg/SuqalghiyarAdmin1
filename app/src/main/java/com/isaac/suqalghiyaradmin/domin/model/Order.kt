package com.isaac.souqalghiyaradmin.domain.model

data class Order(
    val order_id: String = "", // هذا الـ ID يتم جلبه من Document ID
    val order_number: Int = 0,
    val order_status: String = "", // مطابق لـ "pending"
    val vehicle_name: String = "",
    val vehicle_model: String = "",
    val delivery_location: String = "",
    val delivery_fees: Double = 0.0,
    val pic_vin_number: String = "",
    val user_id: String = "",
    val created_at: com.google.firebase.Timestamp? = null,
    val selling_price: Double = 0.0 // سيتم إضافته عند التعديل
)
