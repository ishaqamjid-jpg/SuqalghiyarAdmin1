package com.isaac.souqalghiyaradmin.presentation.users

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersSettingsScreen(
    viewModel: UsersViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val users by viewModel.users.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var userToEdit by remember { mutableStateOf<UserEmp?>(null) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("إدارة موظفي الإدارة") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { userToEdit = null; showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "إضافة")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
            items(users) { user ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(user.Name, style = MaterialTheme.typography.titleMedium)
                            Text("الصلاحية: ${user.Access}", style = MaterialTheme.typography.bodySmall)
                        }
                        IconButton(onClick = { userToEdit = user; showDialog = true }) {
                            Icon(Icons.Default.Edit, contentDescription = "تعديل")
                        }
                        IconButton(onClick = { viewModel.deleteUser(user.Id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "حذف", tint = Color.Red)
                        }
                    }
                }
            }
        }

        if (showDialog) {
            UserEditorDialog(
                user = userToEdit,
                onDismiss = { showDialog = false },
                onSave = { name, username, password, access ->
                    viewModel.saveUser(userToEdit?.Id ?: "", name, username, password, access)
                    showDialog = false
                }
            )
        }
    }
}
