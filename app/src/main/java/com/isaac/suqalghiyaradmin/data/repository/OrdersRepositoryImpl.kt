package com.isaac.souqalghiyaradmin.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.isaac.souqalghiyaradmin.domain.model.Order
import com.isaac.souqalghiyaradmin.domain.model.OrderItem
import com.isaac.souqalghiyaradmin.domain.repository.OrdersRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) : OrdersRepository {
    
    override fun getPendingOrders() = callbackFlow {
        val listener = db.collection("orders").whereEqualTo("order_status", "pending")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) trySend(snapshot.toObjects(Order::class.java))
            }
        awaitClose { listener.remove() }
    }

    override suspend fun updateOrder(orderId: String, price: Double, status: String) {
        db.collection("orders").document(orderId)
            .update(mapOf("selling_price" to price, "order_status" to status)).await()
    }

    override suspend fun getOrderItems(orderId: String): List<OrderItem> {
        return db.collection("orders").document(orderId).collection("items").get().await()
            .toObjects(OrderItem::class.java)
    }

    override suspend fun getAllOrders(): List<Order> {
        return db.collection("orders").get().await().toObjects(Order::class.java)
    }
}
