package com.isaac.souqalghiyaradmin.presentation.ads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isaac.souqalghiyaradmin.domain.model.Ad
import com.isaac.souqalghiyaradmin.domain.repository.AdsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdsViewModel @Inject constructor(private val repository: AdsRepository) : ViewModel() {

    val ads = repository.getAds()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addAd(title: String, desc: String) {
        viewModelScope.launch {
            repository.addAd(Ad(title = title, description = desc))
        }
    }

    fun deleteAd(adId: String) {
        viewModelScope.launch {
            repository.deleteAd(adId)
        }
    }
}

