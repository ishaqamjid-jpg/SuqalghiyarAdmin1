package com.isaac.souqalghiyaradmin

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// هذا الـ Annotation هو الذي يخبر Hilt ببدء تهيئة النظام بالكامل
@HiltAndroidApp
class SouqAlghiyarAdminApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // يمكنك هنا تهيئة مكتبات أخرى مثل Firebase أو Crashlytics إذا لزم الأمر
    }
}
