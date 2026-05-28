package com.isaac.souqalghiyaradmin.presentation.ads

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdsManagementScreen(
    viewModel: AdsViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val ads by viewModel.ads.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("إدارة الإعلانات") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.addAd("عنوان جديد", "وصف تجريبي") }) {
                Icon(Icons.Default.Add, contentDescription = "إضافة")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(ads) { ad ->
                Card(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                    ListItem(
                        headlineContent = { Text(ad.title) },
                        supportingContent = { Text(ad.description) },
                        trailingContent = {
                            IconButton(onClick = { viewModel.deleteAd(ad.id) }) {
                                Icon(Icons.Default.Delete, null, tint = Color.Red)
                            }
                        }
                    )
                }
            }
        }
    }
}
