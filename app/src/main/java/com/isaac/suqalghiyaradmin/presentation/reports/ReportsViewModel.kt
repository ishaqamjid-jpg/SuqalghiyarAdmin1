package com.isaac.souqalghiyaradmin.presentation.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isaac.souqalghiyaradmin.domain.repository.OrdersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReportStats(
    val totalOrders: Int = 0,
    val completedOrders: Int = 0,
    val totalRevenue: Double = 0.0
)

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val repository: OrdersRepository
) : ViewModel() {
    
    private val _stats = MutableStateFlow(ReportStats())
    val stats = _stats.asStateFlow()

    init {
        loadStats()
    }

    private fun loadStats() {
        viewModelScope.launch {
            val allOrders = repository.getAllOrders()
            val completed = allOrders.filter { it.order_status == "completed" }
            _stats.value = ReportStats(
                totalOrders = allOrders.size,
                completedOrders = completed.size,
                totalRevenue = completed.sumOf { it.selling_price }
            )
        }
    }
}
