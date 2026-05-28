package com.isaac.souqalghiyaradmin.presentation.constants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.isaac.souqalghiyaradmin.domain.model.SparePartCategory
import com.isaac.souqalghiyaradmin.domain.model.QualityType
import com.isaac.souqalghiyaradmin.domain.repository.ConstantsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConstantsViewModel @Inject constructor(
    private val repository: ConstantsRepository,
    private val db: FirebaseFirestore // حقن الفايربيز مباشرة للإضافة والحذف فقط
) : ViewModel() {

    // استدعاء البيانات باستخدام الـ Repository
    val categories: StateFlow<List<SparePartCategory>> = repository.getSparePartCategories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val qualities: StateFlow<List<QualityType>> = repository.getQualityTypes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // دالة لإضافة فئة قطع غيار جديدة
    fun addCategory(categoryName: String) {
        viewModelScope.launch {
            db.collection("spare_parts_categories").add(mapOf("spare_parts_categories" to categoryName))
        }
    }

    // دالة لإضافة نوع جودة جديد
    fun addQualityType(typeName: String) {
        viewModelScope.launch {
            db.collection("quality_types").add(mapOf("quality_types" to typeName))
        }
    }

    // دالة لحذف فئة قطع غيار
    fun deleteCategory(id: String) {
        viewModelScope.launch {
            db.collection("spare_parts_categories").document(id).delete()
        }
    }

    // دالة لحذف نوع جودة
    fun deleteQualityType(id: String) {
        viewModelScope.launch {
            db.collection("quality_types").document(id).delete()
        }
    }
}
