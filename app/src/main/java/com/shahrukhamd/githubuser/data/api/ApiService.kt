package com.shahrukhamd.githubuser.data.api

import com.shahrukhamd.githubuser.data.model.ApiUserSearchResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET
    suspend fun getUser(): Response<ApiUserSearchResponse>
}