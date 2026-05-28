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
    private val db: FirebaseFirestore
) : ViewModel() {

    // جلب البيانات من الـ Repository
    val categories: StateFlow<List<SparePartCategory>> = repository.getSparePartCategories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val qualities: StateFlow<List<QualityType>> = repository.getQualityTypes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- عمليات إضافة البيانات ---

    fun addCategory(categoryName: String) {
        viewModelScope.launch {
            db.collection("spare_parts_categories")
                .add(mapOf("spare_parts_categories" to categoryName))
        }
    }

    fun addQualityType(typeName: String) {
        viewModelScope.launch {
            db.collection("quality_types")
                .add(mapOf("quality_types" to typeName))
        }
    }

    // --- عمليات تحديث البيانات ---

    fun updateCategory(id: String, newName: String) {
        viewModelScope.launch {
            db.collection("spare_parts_categories")
                .document(id)
                .update("spare_parts_categories", newName)
        }
    }

    fun updateQualityType(id: String, newName: String) {
        viewModelScope.launch {
            db.collection("quality_types")
                .document(id)
                .update("quality_types", newName)
        }
    }

    // --- عمليات حذف البيانات ---

    fun deleteCategory(id: String) {
        viewModelScope.launch {
            db.collection("spare_parts_categories")
                .document(id)
                .delete()
        }
    }

    fun deleteQualityType(id: String) {
        viewModelScope.launch {
            db.collection("quality_types")
                .document(id)
                .delete()
        }
    }
}
