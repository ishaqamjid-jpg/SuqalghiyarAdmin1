package com.isaac.souqalghiyaradmin.presentation.reports

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class ReportStats(
    val totalOrders: Int = 0,
    val completedOrders: Int = 0,
    val totalRevenue: Double = 0.0
)

@HiltViewModel
class ReportsViewModel @Inject constructor(private val db: FirebaseFirestore) : ViewModel() {
    private val _stats = MutableStateFlow(ReportStats())
    val stats = _stats.asStateFlow()

    init {
        loadStats()
    }

    private fun loadStats() {
        db.collection("orders").addSnapshotListener { snapshot, _ ->
            val orders = snapshot?.toObjects(com.isaac.souqalghiyaradmin.domain.model.Order::class.java) ?: emptyList()
            val completed = orders.filter { it.order_status == "completed" }
            _stats.value = ReportStats(
                totalOrders = orders.size,
                completedOrders = completed.size,
                totalRevenue = completed.sumOf { it.selling_price }
            )
        }
    }
}
