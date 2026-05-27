package com.isaac.suqalghiyaradmin

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.isaac.suqalghiyaradmin.ui.theme.SuqalghiyarAdminTheme
import com.isaac.souqalghiyaradmin.presentation.login.LoginScreen // تأكد من مسار الاستيراد الصحيح لشاشة الدخول
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuqalghiyarAdminTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    val navController = rememberNavController()

                    // فحص الجلسة لمعرفة ما إذا كان المدير مسجلاً للدخول مسبقاً
                    val sharedPref = context.getSharedPreferences("admin_prefs", Context.MODE_PRIVATE)
                    val isLoggedIn = sharedPref.getBoolean("is_logged_in", false)
                    
                    // تحديد شاشة البداية بناءً على حالة تسجيل الدخول
                    val startDest = if (isLoggedIn) "dashboard" else "login"

                    NavHost(navController = navController, startDestination = startDest) {
                        
                        // --- 1. شاشة تسجيل الدخول ---
                        composable("login") {
                            LoginScreen(
                                navigateToDashboard = { accessType ->
                                    navController.navigate("dashboard") {
                                        // مسح شاشة الدخول من الذاكرة لكي لا يعود إليها عند ضغط زر الرجوع
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            )
                        }

                        // --- 2. الشاشة الرئيسية (الداش بورد) ---
                        composable("dashboard") {
                            // واجهة مؤقتة للداش بورد لحين تصميمها
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "مرحباً بك في لوحة تحكم سوق الغيار",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}
