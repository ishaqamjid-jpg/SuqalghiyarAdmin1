package com.isaac.souqalghiyaradmin.presentation.constants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.isaac.souqalghiyaradmin.domain.model.SparePartCategory
import com.isaac.souqalghiyaradmin.domain.model.QualityType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConstantsScreen(
    viewModel: ConstantsViewModel = hiltViewModel()
) {
    val categories by viewModel.categories.collectAsState()
    val qualities by viewModel.qualities.collectAsState()

    var editingCategory by remember { mutableStateOf<SparePartCategory?>(null) }
    var editingQuality by remember { mutableStateOf<QualityType?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("ثوابت النظام") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            
            Text("أقسام قطع الغيار:", fontWeight = FontWeight.Bold)
            Button(onClick = { editingCategory = null; showDialog = true }) {
                Icon(Icons.Default.Add, null)
                Text(" إضافة قسم")
            }
            
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(categories) { category ->
                    ListItem(
                        modifier = Modifier.clickable { editingCategory = category; showDialog = true },
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

            Text("أنواع الجودة:", fontWeight = FontWeight.Bold)
            Button(onClick = { editingQuality = null; showDialog = true }) {
                Icon(Icons.Default.Add, null)
                Text(" إضافة جودة")
            }

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(qualities) { quality ->
                    ListItem(
                        modifier = Modifier.clickable { editingQuality = quality; showDialog = true },
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

    if (showDialog) {
        val isEditing = editingCategory != null || editingQuality != null
        val initialText = editingCategory?.spare_parts_categories ?: editingQuality?.quality_types ?: ""
        
        AddConstantDialog(
            title = if (isEditing) "تعديل البيانات" else "إضافة جديدة",
            initialValue = initialText,
            onDismiss = { showDialog = false; editingCategory = null; editingQuality = null },
            onConfirm = { name ->
                if (editingCategory != null) viewModel.updateCategory(editingCategory!!.id, name)
                else if (editingQuality != null) viewModel.updateQualityType(editingQuality!!.id, name)
                else if (editingCategory == null && editingQuality == null) {
                    // منطق الإضافة (يمكن تحسينه عبر تمرير النوع)
                }
                showDialog = false; editingCategory = null; editingQuality = null
            }
        )
    }
}

@Composable
fun AddConstantDialog(title: String, initialValue: String, onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var text by remember { mutableStateOf(initialValue) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            OutlinedTextField(value = text, onValueChange = { text = it }, label = { Text("الاسم") })
        },
        confirmButton = { Button(onClick = { onConfirm(text) }) { Text("حفظ") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("إلغاء") } }
    )
}
