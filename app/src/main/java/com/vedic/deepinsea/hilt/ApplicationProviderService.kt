package com.vedic.deepinsea.hilt

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationProviderService {

     /*@Provides
     @Singleton
     fun provideApplicationContext(): Context {
         return provideApplicationContext()
     }*/

}