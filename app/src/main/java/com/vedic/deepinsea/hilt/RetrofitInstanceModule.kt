package com.vedic.deepinsea.hilt

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vedic.deepinsea.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitInstanceModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(): Retrofit {

        val httpLoggingIntercepted = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val apiKeyInterceptor = OkHttpClient.Builder()
            .addInterceptor(httpLoggingIntercepted)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original = chain.request()
                var requestBuilder = original.newBuilder().header(
                        "Authorization", BuildConfig.PEXELS_API_KEY
                    ) // Replace 'YOUR_API_KEY' with your actual API key
                    .method(original.method, original.body)

                var request = requestBuilder.build()
                var response = chain.proceed(request)
                if (!response.isSuccessful && response.code == 401) {
                    response.close()
                    Log.d("Abhishek", "using 2nd api key")
                    requestBuilder = original.newBuilder().header(
                        "Authorization", BuildConfig.PEXELS_API_KEY_2
                    ).method(original.method, original.body)
                    request = requestBuilder.build()
                    response = chain.proceed(request)
                }
                response
            }


//        val httpClient = OkHttpClient().newBuilder()
//            .addInterceptor(apiKeyInterceptor)
//            .readTimeout(60, TimeUnit.SECONDS)

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(apiKeyInterceptor.build())
            .baseUrl(BuildConfig.PEXELS_BASE_URL)
            .build()
    }
}