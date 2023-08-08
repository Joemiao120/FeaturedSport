package com.example.featuredsport.di

import com.example.featuredsport.domain.DefaultGetRandomSport
import com.example.featuredsport.domain.GetRandomSport
import com.example.featuredsport.repository.ContentRepository
import com.example.featuredsport.repository.DefaultContentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsContentRepository(
        contentRepository: DefaultContentRepository,
    ): ContentRepository

    @Binds
    fun bindsGetRandomSport(
        getRandomSport: DefaultGetRandomSport,
    ): GetRandomSport

}