package com.isaac.souqalghiyaradmin.presentation.users

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.isaac.souqalghiyaradmin.domain.model.UserEmp

@Composable
fun UserEditorDialog(
    user: UserEmp?,
    onDismiss: () -> Unit,
    onSave: (String, String, String, String, String) -> Unit
) {
    var name by remember { mutableStateOf(user?.name ?: "") }
    var username by remember { mutableStateOf(user?.username ?: "") }
    var password by remember { mutableStateOf(user?.password ?: "") }
    var access by remember { mutableStateOf(user?.access ?: "employ") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (user == null) "إضافة موظف جديد" else "تعديل بيانات الموظف") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("الاسم") })
                OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("اسم المستخدم") })
                OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("كلمة المرور") })
                
                Text("الصلاحية:")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = access == "admin", onClick = { access = "admin" })
                    Text("مدير")
                    Spacer(Modifier.width(16.dp))
                    RadioButton(selected = access == "employ", onClick = { access = "employ" })
                    Text("موظف")
                }
            }
        },
        confirmButton = {
            Button(onClick = { onSave(user?.id ?: "", name, username, password, access) }) {
                Text("حفظ")
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("إلغاء") } }
    )
}
