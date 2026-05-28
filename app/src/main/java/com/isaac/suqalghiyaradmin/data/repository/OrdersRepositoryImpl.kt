package com.isaac.souqalghiyaradmin.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.isaac.souqalghiyaradmin.domain.model.Order
import com.isaac.souqalghiyaradmin.domain.model.OrderItem
import com.isaac.souqalghiyaradmin.domain.repository.OrdersRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : OrdersRepository {

    // 1. جلب الطلبات المعلقة فقط (Pending Orders)
    override fun getPendingOrders(): Flow<List<Order>> = callbackFlow {
        val listener = db.collection("orders")
            .whereEqualTo("order_status", "pending")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val orders = snapshot.documents.map { doc ->
                        // تحويل المستند مع حفظ الـ ID الخاص به
                        doc.toObject(Order::class.java)?.copy(order_id = doc.id) ?: Order()
                    }
                    trySend(orders)
                }
            }
        awaitClose { listener.remove() }
    }

    // 2. جلب القطع الخاصة بطلب معين (Sub-collection)
    override suspend fun getOrderItems(orderId: String): List<OrderItem> {
        return try {
            // التأكد من أن المسار هو: orders/{orderId}/items
            val querySnapshot = db.collection("orders")
                .document(orderId)
                .collection("items")
                .get()
                .await()

            querySnapshot.toObjects(OrderItem::class.java)
        } catch (e: Exception) {
            // طباعة الخطأ لمعرفة السبب في Logcat
            e.printStackTrace()
            emptyList()
        }
    }

    // 3. تحديث حالة الطلب وسعر البيع
    override suspend fun updateOrder(orderId: String, sellingPrice: Double, status: String) {
        db.collection("orders")
            .document(orderId)
            .update(
                mapOf(
                    "selling_price" to sellingPrice,
                    "order_status" to status
                )
            ).await()

    }
    override suspend fun getAllOrders(): List<Order> {
        return db.collection("orders").get().await().toObjects(Order::class.java)}
}