/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.data.api

import com.shahrukhamd.githubuser.data.model.ApiUserSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("/search/users")
    suspend fun getPaginatedUser(@Query("q") query: String, @Query("page") page: Int): Response<ApiUserSearchResponse>
}