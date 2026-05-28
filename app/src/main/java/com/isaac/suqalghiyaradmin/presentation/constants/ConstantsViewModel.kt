package com.isaac.souqalghiyaradmin.presentation.constants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isaac.souqalghiyaradmin.domain.repository.ConstantsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ConstantsViewModel @Inject constructor(repository: ConstantsRepository) : ViewModel() {

    val categories = repository.getSparePartCategories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val qualities = repository.getQualityTypes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}

