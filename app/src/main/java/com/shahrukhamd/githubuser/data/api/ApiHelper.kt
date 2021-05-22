/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.data.api

import com.shahrukhamd.githubuser.data.model.ApiUserSearchResponse
import retrofit2.Response

interface ApiHelper {

    suspend fun getUser(query: String): Response<ApiUserSearchResponse>
}