package com.isaac.souqalghiyaradmin.presentation.users

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersSettingsScreen(viewModel: UsersViewModel = hiltViewModel()) {
    val users by viewModel.users.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var userToEdit by remember { mutableStateOf<UserEmp?>(null) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("إدارة الموظفين") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { userToEdit = null; showDialog = true }) {
                Icon(Icons.Default.Add, "إضافة")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(users) { user ->
                ListItem(
                    headlineContent = { Text(user.name) },
                    supportingContent = { Text("الصلاحية: ${user.access}") },
                    trailingContent = {
                        Row {
                            IconButton(onClick = { userToEdit = user; showDialog = true }) {
                                Icon(Icons.Default.Edit, "تعديل")
                            }
                            IconButton(onClick = { viewModel.deleteUser(user.id) }) {
                                Icon(Icons.Default.Delete, "حذف", tint = Color.Red)
                            }
                        }
                    }
                )
            }
        }

        if (showDialog) {
            UserEditorDialog(
                user = userToEdit,
                onDismiss = { showDialog = false },
                onSave = { id, name, username, password, access ->
                    viewModel.saveUser(id, name, username, password, access)
                    showDialog = false
                }
            )
        }
    }
}
