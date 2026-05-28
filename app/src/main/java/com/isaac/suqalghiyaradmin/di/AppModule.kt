package com.isaac.souqalghiyaradmin.di

import com.google.firebase.firestore.FirebaseFirestore
import com.isaac.souqalghiyaradmin.data.repository.*
import com.isaac.souqalghiyaradmin.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideOrdersRepository(db: FirebaseFirestore): OrdersRepository = OrdersRepositoryImpl(db)

    @Provides
    @Singleton
    fun provideAdsRepository(db: FirebaseFirestore): AdsRepository = AdsRepositoryImpl(db)

    @Provides
    @Singleton
    fun provideConstantsRepository(db: FirebaseFirestore): ConstantsRepository = ConstantsRepositoryImpl(db)
}