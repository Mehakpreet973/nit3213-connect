package com.example.nit3213connect.di

import com.example.nit3213connect.data.repo.NitRepository
import com.example.nit3213connect.data.repo.NitRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides @Singleton
    fun provideNitRepository(impl: NitRepositoryImpl): NitRepository = impl
}
