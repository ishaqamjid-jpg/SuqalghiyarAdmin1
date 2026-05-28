package com.isaac.souqalghiyaradmin.presentation.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isaac.souqalghiyaradmin.domain.model.Order
import com.isaac.souqalghiyaradmin.domain.repository.OrdersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.isaac.souqalghiyaradmin.domain.model.OrderItem

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val repository: OrdersRepository
) : ViewModel() {

    // تحويل الـ Flow من الـ Repository إلى StateFlow داخل الـ ViewModel
    val orders: StateFlow<List<Order>> = repository.getPendingOrders()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateOrder(orderId: String, sellingPrice: Double, status: String) {
        viewModelScope.launch {
            repository.updateOrder(orderId, sellingPrice, status)
        }
    }
    // داخل OrdersViewModel
    // داخل OrdersViewModel.kt
    suspend fun getOrderItems(orderId: String): List<OrderItem> {
        return repository.getOrderItems(orderId)
    }
    }
    // أو بشكل أبسط للتعامل مع الـ Coroutines في الواجهة:
    suspend fun fetchOrderItems(orderId: String): List<OrderItem> {
        return repository.getOrderItems(orderId)
    }
    // جلب القطع الفرعية
    suspend fun getItems(orderId: String) = repository.getOrderItems(orderId)
}
