package com.isaac.souqalghiyaradmin.presentation.constants

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConstantsScreen(
    viewModel: ConstantsViewModel = hiltViewModel()
) {
    val categories by viewModel.categories.collectAsState()
    val qualities by viewModel.qualities.collectAsState()
    
    var showAddCategoryDialog by remember { mutableStateOf(false) }
    var showAddQualityDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("ثوابت النظام") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            
            // قسم فئات قطع الغيار
            Text("أقسام قطع الغيار:", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Button(onClick = { showAddCategoryDialog = true }, modifier = Modifier.padding(vertical = 8.dp)) {
                Icon(Icons.Default.Add, null)
                Text(" إضافة قسم")
            }
            
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(categories) { category ->
                    ListItem(
                        headlineContent = { Text(category.spare_parts_categories) },
                        trailingContent = {
                            IconButton(onClick = { viewModel.deleteCategory(category.id) }) {
                                Icon(Icons.Default.Delete, null, tint = Color.Red)
                            }
                        }
                    )
                }
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // قسم أنواع الجودة
            Text("أنواع الجودة:", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Button(onClick = { showAddQualityDialog = true }, modifier = Modifier.padding(vertical = 8.dp)) {
                Icon(Icons.Default.Add, null)
                Text(" إضافة جودة")
            }

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(qualities) { quality ->
                    ListItem(
                        headlineContent = { Text(quality.quality_types) },
                        trailingContent = {
                            IconButton(onClick = { viewModel.deleteQualityType(quality.id) }) {
                                Icon(Icons.Default.Delete, null, tint = Color.Red)
                            }
                        }
                    )
                }
            }
        }
    }

    // مربعات الحوار للإضافة (Dialogs)
    if (showAddCategoryDialog) {
        AddConstantDialog(
            title = "إضافة قسم جديد",
            onDismiss = { showAddCategoryDialog = false },
            onConfirm = { name -> viewModel.addCategory(name); showAddCategoryDialog = false }
        )
    }

    if (showAddQualityDialog) {
        AddConstantDialog(
            title = "إضافة نوع جودة جديد",
            onDismiss = { showAddQualityDialog = false },
            onConfirm = { name -> viewModel.addQualityType(name); showAddQualityDialog = false }
        )
    }
}

@Composable
fun AddConstantDialog(title: String, onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("الاسم") }
            )
        },
        confirmButton = {
            Button(onClick = { onConfirm(text) }) { Text("حفظ") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("إلغاء") }
        }
    )
}
