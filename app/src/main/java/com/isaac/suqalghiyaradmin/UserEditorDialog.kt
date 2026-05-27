package com.isaac.souqalghiyaradmin.presentation.users

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UserEditorDialog(
    user: UserEmp?,
    onDismiss: () -> Unit,
    onSave: (String, String, String, String) -> Unit
) {
    var name by remember { mutableStateOf(user?.Name ?: "") }
    var username by remember { mutableStateOf(user?.Usrename ?: "") }
    var password by remember { mutableStateOf(user?.Password ?: "") }
    var access by remember { mutableStateOf(user?.Access ?: "employ") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (user == null) "إضافة موظف جديد" else "تعديل بيانات الموظف") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("الاسم الكامل") })
                OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("اسم المستخدم") })
                OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("كلمة المرور") })
                
                // اختيار الصلاحية
                Text("الصلاحية:")
                Row {
                    RadioButton(selected = access == "admin", onClick = { access = "admin" })
                    Text("مدير (Admin)")
                    Spacer(Modifier.width(16.dp))
                    RadioButton(selected = access == "employ", onClick = { access = "employ" })
                    Text("موظف (Employ)")
                }
            }
        },
        confirmButton = {
            Button(onClick = { onSave(name, username, password, access) }) {
                Text("حفظ")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("إلغاء") }
        }
    )
}
