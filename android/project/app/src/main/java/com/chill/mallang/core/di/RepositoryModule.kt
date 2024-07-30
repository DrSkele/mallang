package com.chill.mallang.core.di

import com.chill.mallang.data.repository.local.DataStoreRepository
import com.chill.mallang.data.repository.remote.FirebaseRepository
import com.chill.mallang.data.repository.remote.UserRepository
import com.chill.mallang.data.repositoyimpl.local.DataStoreRepositoryImpl
import com.chill.mallang.data.repositoyimpl.remote.FirebaseRepositoryImpl
import com.chill.mallang.data.repositoyimpl.remote.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideFirebaseRepository(
        firebaseRepositoryImpl: FirebaseRepositoryImpl
    ): FirebaseRepository

    @Binds
    @Singleton
    fun provideUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    fun provideDataStoreRepository(
        dataStoreRepositoryImpl: DataStoreRepositoryImpl
    ): DataStoreRepository
}