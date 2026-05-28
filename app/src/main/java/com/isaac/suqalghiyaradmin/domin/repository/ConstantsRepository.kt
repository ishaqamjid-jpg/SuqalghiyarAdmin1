package com.isaac.souqalghiyaradmin.domain.repository

import com.isaac.souqalghiyaradmin.domain.model.SparePartCategory
import com.isaac.souqalghiyaradmin.domain.model.QualityType
import kotlinx.coroutines.flow.Flow

interface ConstantsRepository {
    fun getSparePartCategories(): Flow<List<SparePartCategory>>
    fun getQualityTypes(): Flow<List<QualityType>>
}
