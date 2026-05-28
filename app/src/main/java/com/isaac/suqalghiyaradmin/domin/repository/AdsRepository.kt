package com.isaac.souqalghiyaradmin.domain.repository

import com.isaac.souqalghiyaradmin.domain.model.Ad
import kotlinx.coroutines.flow.Flow

interface AdsRepository {
    fun getAds(): Flow<List<Ad>>
    suspend fun addAd(ad: Ad)
    suspend fun deleteAd(adId: String)
}
