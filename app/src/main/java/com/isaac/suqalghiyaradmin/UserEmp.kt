package com.isaac.souqalghiyaradmin.domain.model

data class UserEmp(
    val id: String = "",       // هذا سيكون مخزناً فيه الـ Document ID بعد جلبه من Firebase
    val username: String = "", 
    val password: String = "", 
    val name: String = "",     
    val access: String = "employ"
)
