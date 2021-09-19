/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shahrukhamd.githubuser.data.api.GithubService
import com.shahrukhamd.githubuser.data.base.AppDatabase
import com.shahrukhamd.githubuser.data.model.GithubUser
import com.shahrukhamd.githubuser.data.source.GithubPagingSource
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

private const val NETWORK_PAGE_SIZE = 30

class SearchRepositoryImpl @Inject constructor(
    private val service: GithubService,
    private val database: AppDatabase
): SearchRepository {

    override fun getPaginatedUser(query: String): Flow<PagingData<GithubUser>> {
        return Pager(
            config = PagingConfig(NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { GithubPagingSource(database, service, query) }
        ).flow
    }

    override suspend fun getPagingStarredUsers(): Flow<PagingData<GithubUser>> {
        return Pager(
            config = PagingConfig(NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory =  { database.userItemDao().getPagingStarredUsers() }
        ).flow
    }

    override suspend fun getUserDetailsAndUpdateDb(username: String): GithubUser? {
        // todo find a better solution to prevent UnknownHostException
        return try {
            service.getUserDetails(username).body()?.apply {
                val localUser = database.userItemDao().getUser(id)
                isUserStared = localUser?.isUserStared == true
                database.userItemDao().updateUser(this)
            }
        } catch (e: IOException) {
            Timber.e(e) // Retrofit﹕ java.net.UnknownHostException
            return database.userItemDao().getUser(username)
        }
    }

    override suspend fun updateUser(user: GithubUser) {
        database.userItemDao().updateUser(user)
    }
}