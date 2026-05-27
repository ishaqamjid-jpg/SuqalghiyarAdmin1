
package com.isaac.souqalghiyaradmin.di

import com.isaac.souqalghiyaradmin.data.repository.AdminRepositoryImpl
import com.isaac.souqalghiyaradmin.domain.repository.AdminRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindAdminRepository(
        adminRepositoryImpl: AdminRepositoryImpl
    ): AdminRepository
}
