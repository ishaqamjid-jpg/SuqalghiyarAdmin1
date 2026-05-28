package com.isaac.souqalghiyaradmin

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SouqAlghiyarAdminApp : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // تهيئة Firebase يدوياً لضمان جاهزيته قبل أي عملية اتصال في التطبيق
        FirebaseApp.initializeApp(this)
        
        // يمكنك إضافة تهيئات أخرى هنا، مثل:
        // - مكتبات التخزين المؤقت (Cache)
        // - مكتبات تسجيل الأخطاء (Crashlytics)
        // - مكتبات الإشعارات (Firebase Messaging)
    }
}
