package com.isaac.souqalghiyaradmin.presentation.orders

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.isaac.souqalghiyaradmin.domain.model.Order
import com.isaac.souqalghiyaradmin.domain.model.OrderItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersManagementScreen(
    viewModel: OrdersViewModel = hiltViewModel()
) {
    val orders by viewModel.orders.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("إدارة الطلبات المعلقة") }) }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(orders) { order ->
                OrderExpandableCard(order = order, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun OrderExpandableCard(order: Order, viewModel: OrdersViewModel) {
    var expanded by remember { mutableStateOf(false) }
    var items by remember { mutableStateOf<List<OrderItem>>(emptyList()) }
    var price by remember { mutableStateOf("") }

    // جلب القطع فقط عند التوسع
    LaunchedEffect(expanded) {
        if (expanded && items.isEmpty()) {
            items = viewModel.getOrderItems(order.order_id)
        }
    }

    Card(modifier = Modifier.padding(8.dp).fillMaxWidth().clickable { expanded = !expanded }) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("طلب: ${order.order_id}", fontWeight = FontWeight.Bold)
                    Text("المركبة: ${order.vehicle_name}")
                }
                Icon(if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore, null)
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Divider()
                    Text("القطع المطلوبة:", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(vertical = 4.dp))
                    
                    items.forEach { item ->
                        Text("• ${item.part_name} | الكمية: ${item.quantity}")
                    }

                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        label = { Text("سعر البيع الإجمالي") },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    )

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = { viewModel.updateOrder(order.order_id, price.toDoubleOrNull() ?: 0.0, "rejected") }) {
                            Text("رفض", color = Color.Red)
                        }
                        Button(onClick = { viewModel.updateOrder(order.order_id, price.toDoubleOrNull() ?: 0.0, "approved") }) {
                            Text("موافقة")
                        }
                    }
                }
            }
        }
    }
}
