package com.isaac.souqalghiyaradmin.presentation.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.isaac.souqalghiyaradmin.domain.model.Order
import com.isaac.souqalghiyaradmin.domain.model.OrderItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val db: FirebaseFirestore
) : ViewModel() {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    init {
        fetchPendingOrders()
    }

    private fun fetchPendingOrders() {
        // مراقبة الطلبات المعلقة فقط
        db.collection("orders")
            .whereEqualTo("order_status", "pending")
            .addSnapshotListener { snapshot, e ->
                if (snapshot != null) {
                    _orders.value = snapshot.documents.map { doc ->
                        doc.toObject(Order::class.java)?.copy(order_id = doc.id) ?: Order()
                    }
                }
            }
    }

    suspend fun getOrderItems(orderId: String): List<OrderItem> {
        return try {
            val snapshot = db.collection("orders")
                .document(orderId)
                .collection("items")
                .get()
                .await()
            
            snapshot.toObjects(OrderItem::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun updateOrder(orderId: String, sellingPrice: Double, status: String) {
        viewModelScope.launch {
            try {
                db.collection("orders").document(orderId)
                    .update(
                        mapOf(
                            "selling_price" to sellingPrice,
                            "order_status" to status
                        )
                    ).await()
            } catch (e: Exception) {
                // معالجة الخطأ
            }
        }
    }
}
