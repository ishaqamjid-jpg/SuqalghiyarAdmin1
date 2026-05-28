package com.isaac.souqalghiyaradmin.domain.model

data class OrderItem(
    val item_id: String = "",
    val part_name: String = "",
    val quantity: Int = 1,
    val quality_type: String = "",
    val description: String = "",
    val comments: String = "",
    val purchase_price: Double = 0.0,
    val selling_price: Double = 0.0
)