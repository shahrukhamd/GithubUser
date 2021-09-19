/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.di.module

import android.app.Application
import android.content.Context
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
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
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
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String,
                        converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideCache(@ApplicationContext context: Context): Cache {
        val cacheSize = (5 * 1024 * 1024).toLong()  // 5 mb cache size
        return Cache(context.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        val onlineInterceptor = Interceptor { chain ->
            val builder = chain.proceed(chain.request()).newBuilder()

            // read from cache data for 60 seconds, older data will be cleared
            builder.header("Cache-Control", "public, max-age=" + 60)
            builder.removeHeader("Pragma") // remove server side caching protocol

            builder.build()
        }

        val authInterceptor = Interceptor { chain ->
            val request = chain.request()
            val builder = request.newBuilder()
            val authToken = ""
            if (authToken.isNotBlank()) {
                // add auth token to your request header
                // builder.addHeader()
            }
            chain.proceed(builder.build())
        }

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .addNetworkInterceptor(onlineInterceptor)
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