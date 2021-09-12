/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.di.module

import android.app.Application
import com.shahrukhamd.githubuser.BuildConfig
import com.shahrukhamd.githubuser.data.api.GithubService
import com.shahrukhamd.githubuser.data.base.AppDatabase
import com.shahrukhamd.githubuser.data.repository.SearchRepository
import com.shahrukhamd.githubuser.data.repository.SearchRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class QAppDatabase

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class QGithubService

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String,
                        converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @QGithubService
    fun provideApiService(retrofit: Retrofit): GithubService = retrofit.create(GithubService::class.java)

    @Provides
    @Singleton
    @QAppDatabase
    fun provideDatabase(app: Application) = AppDatabase.getInstance(app)
}

/**
 * A separate module just for the [SearchRepository] so this can be used for injecting fake one in test(s)
 */
@Module
@InstallIn(SingletonComponent::class)
object SearchRepositoryModule {
    @Provides
    @Singleton
    fun provideSearchRepository(
        @AppModule.QGithubService service: GithubService,
        @AppModule.QAppDatabase database: AppDatabase): SearchRepository {
        return SearchRepositoryImpl(service, database)
    }
}