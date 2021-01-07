package com.shakil.newzcompose.di

import android.content.Context
import coil.ImageLoader
import coil.util.CoilUtils
import com.shakil.newzcompose.network.MoshiFactory
import com.shakil.newzcompose.network.NewsApiKeyInterceptor
import com.shakil.newzcompose.network.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(NewsApiKeyInterceptor())
            //.addInterceptor(ChuckerInterceptor.Builder(context).build())
            .cache(CoilUtils.createDefaultCache(context))
            .build()
    }

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient
    ): ImageLoader {
        return ImageLoader.Builder(context)
            .okHttpClient { okHttpClient }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(
                "https://newsapi.org"
            )
            .addConverterFactory(MoshiConverterFactory.create(MoshiFactory.create()))
            .build()
    }

    @Provides
    @Singleton
    fun provideDisneyService(retrofit: Retrofit): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }


}