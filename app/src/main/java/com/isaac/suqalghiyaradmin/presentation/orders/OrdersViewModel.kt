package com.isaac.souqalghiyaradmin.presentation.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.isaac.souqalghiyaradmin.domain.model.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor() : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders = _orders.asStateFlow()

    init {
        fetchPendingOrders()
    }

    private fun fetchPendingOrders() {
        db.collection("orders")
            .whereEqualTo("order_status", "pending")
            .addSnapshotListener { snapshot, _ ->
                _orders.value = snapshot?.toObjects(Order::class.java) ?: emptyList()
            }
    }

    fun updateOrder(orderId: String, sellingPrice: Double, status: String) {
        viewModelScope.launch {
            db.collection("orders").document(orderId)
                .update(
                    mapOf(
                        "selling_price" to sellingPrice,
                        "order_status" to status
                    )
                )
        }
    }
}

