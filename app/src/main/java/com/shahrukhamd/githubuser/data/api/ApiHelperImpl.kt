package com.shahrukhamd.githubuser.data.api

import com.shahrukhamd.githubuser.data.model.ApiUserSearchResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    suspend fun getUser(): Response<ApiUserSearchResponse> = apiService.getUser()
}