package com.isaac.souqalghiyaradmin

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.isaac.souqalghiyaradmin.presentation.login.LoginScreen
import com.isaac.souqalghiyaradmin.presentation.dashboard.MainDashboardScreen
import com.isaac.souqalghiyaradmin.presentation.users.UsersSettingsScreen
import com.isaac.souqalghiyaradmin.ui.theme.SuqalghiyarAdminTheme
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
                    val navController = rememberNavController()
                    val sharedPref = getSharedPreferences("admin_prefs", Context.MODE_PRIVATE)
                    val isLoggedIn = sharedPref.getBoolean("is_logged_in", false)
                    val adminName = sharedPref.getString("admin_username", "Admin") ?: "Admin"
                    
                    val startDest = if (isLoggedIn) "dashboard" else "login"

                    NavHost(navController = navController, startDestination = startDest) {
                        
                        // --- 1. شاشة تسجيل الدخول ---
                        composable("login") {
                            LoginScreen(
                                navigateToDashboard = { 
                                    navController.navigate("dashboard") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            )
                        }

                        // --- 2. الشاشة الرئيسية (الداش بورد) ---
                        composable("dashboard") {
                            MainDashboardScreen(
                                adminName = adminName,
                                onNavigateToEmpUsers = { navController.navigate("users_emp") },
                                onNavigateToClientUsers = { /* نافذة مستخدمي العميل */ },
                                onNavigateToAds = { /* نافذة الإعلانات */ },
                                onNavigateToOrders = { /* نافذة الطلبات */ },
                                onNavigateToConstants = { /* نافذة الثوابت */ },
                                onNavigateToReports = { /* نافذة التقارير */ },
                                onLogoutClick = {
                                    sharedPref.edit().clear().apply()
                                    navController.navigate("login") {
                                        popUpTo("dashboard") { inclusive = true }
                                    }
                                }
                            )
                        }

                        // --- 3. شاشة إدارة الموظفين ---
                        composable("users_emp") {
                            UsersSettingsScreen(
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                        
                        // يمكنك إضافة باقي الشاشات هنا بنفس الطريقة
                    }
                }
            }
        }
    }
}
