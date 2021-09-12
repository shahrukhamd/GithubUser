package com.shahrukhamd.githubuser.data.repository

import androidx.paging.PagingData
import com.shahrukhamd.githubuser.data.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getPaginatedUser(query: String): Flow<PagingData<GithubUser>>

    suspend fun getUserDetailsAndUpdateDb(username: String): GithubUser?

    suspend fun updateUser(user: GithubUser)
}