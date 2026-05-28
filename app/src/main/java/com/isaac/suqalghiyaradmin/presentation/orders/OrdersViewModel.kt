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

    // الحالة الحالية للطلبات المعلقة
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    // حالة التحميل أثناء تحديث الطلب
    private val _isUpdating = MutableStateFlow(false)
    val isUpdating: StateFlow<Boolean> = _isUpdating.asStateFlow()

    init {
        fetchPendingOrders()
    }

    // جلب الطلبات ذات الحالة "pending" وتحديثها لحظياً
    private fun fetchPendingOrders() {
        db.collection("orders")
            .whereEqualTo("order_status", "pending")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                
                if (snapshot != null) {
                    _orders.value = snapshot.toObjects(Order::class.java)
                }
            }
    }

    // جلب تفاصيل قطع الغيار الفرعية لكل طلب
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

    // تحديث الطلب (موافقة أو رفض)
    fun updateOrder(orderId: String, sellingPrice: Double, status: String) {
        viewModelScope.launch {
            _isUpdating.value = true
            try {
                db.collection("orders").document(orderId)
                    .update(
                        mapOf(
                            "selling_price" to sellingPrice,
                            "order_status" to status
                        )
                    ).await()
            } catch (e: Exception) {
                // يمكنك هنا إضافة معالجة للأخطاء (مثل إظهار رسالة خطأ)
            } finally {
                _isUpdating.value = false
            }
        }
    }
}
