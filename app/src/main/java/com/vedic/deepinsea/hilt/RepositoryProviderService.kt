package com.vedic.deepinsea.hilt

import com.vedic.deepinsea.data.repositories.CollectionsRepository
import com.vedic.deepinsea.data.repositories.CollectionsRepositoryImpl
import com.vedic.deepinsea.data.repositories.HomeRepository
import com.vedic.deepinsea.data.repositories.HomeRepositoryImpl
import com.vedic.deepinsea.data.repositories.VideoRepository
import com.vedic.deepinsea.data.repositories.VideoRepositoryImpl
import com.vedic.deepinsea.data.repositories.WallPaperDetailRepository
import com.vedic.deepinsea.data.repositories.WallPaperDetailRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryProviderService {

    @Provides
    @Singleton
    fun provideHomeRepository(pexelsService: PexelsService): HomeRepository {
        return HomeRepositoryImpl(pexelsService = pexelsService)
    }

    @Provides
    @Singleton
    fun provideWallpaperDetail(pexelsService: PexelsService) : WallPaperDetailRepository {
        return WallPaperDetailRepositoryImpl(pexelsService)
    }

    @Provides
    @Singleton
    fun provideWallpaperVideo(pexelsService: PexelsService) : VideoRepository {
        return VideoRepositoryImpl(pexelsService)
    }

    @Provides
    @Singleton
    fun provideWallpaperCollections(pexelsService: PexelsService): CollectionsRepository {
        return CollectionsRepositoryImpl(pexelsService)
    }
}