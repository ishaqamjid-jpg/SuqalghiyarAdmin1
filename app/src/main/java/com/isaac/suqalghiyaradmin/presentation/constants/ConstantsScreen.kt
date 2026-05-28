package com.isaac.souqalghiyaradmin.presentation.constants

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConstantsScreen(viewModel: ConstantsViewModel = hiltViewModel()) {
    val cities by viewModel.cities.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("إدارة الثوابت (المدن)") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.addConstant("مدينة جديدة", "city") }) {
                Icon(Icons.Default.Add, null)
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(cities) { city ->
                ListItem(
                    headlineContent = { Text(city.name) },
                    trailingContent = {
                        IconButton(onClick = { viewModel.deleteConstant(city.id) }) {
                            Icon(Icons.Default.Delete, null, tint = Color.Red)
                        }
                    }
                )
            }
        }
    }
}
