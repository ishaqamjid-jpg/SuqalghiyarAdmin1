package com.isaac.souqalghiyaradmin.domain.repository

import com.isaac.souqalghiyaradmin.domain.model.Order
import com.isaac.souqalghiyaradmin.domain.model.OrderItem
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {
    fun getPendingOrders(): Flow<List<Order>>
    suspend fun updateOrder(orderId: String, price: Double, status: String)
    suspend fun getOrderItems(orderId: String): List<OrderItem>
    suspend fun getAllOrders(): List<Order> // للتقارير
}
