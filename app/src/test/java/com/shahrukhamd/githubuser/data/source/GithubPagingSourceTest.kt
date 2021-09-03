/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.data.source

import androidx.paging.PagingSource
import com.shahrukhamd.githubuser.data.api.GithubService
import com.shahrukhamd.githubuser.data.model.ApiUserSearchResponse
import com.shahrukhamd.githubuser.data.model.GithubUser
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GithubPagingSourceTest {

    @MockK
    private lateinit var apiUserSearchResponse: ApiUserSearchResponse

    @MockK
    private lateinit var githubService: GithubService

    private lateinit var githubPagingSource: GithubPagingSource

    private val userList = listOf(
        GithubUser(id = 123), GithubUser(id = 456), GithubUser(id = 789)
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        githubPagingSource = GithubPagingSource(githubService, "test")

    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `when github service throw io exception, then same exception should return as load result`() {
        runBlockingTest {

            val ioException = IOException("Some message for exception")

            coEvery {
                githubService.getPaginatedUser(any(), any())
            } throws ioException

            assertEquals(
                actual = githubPagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 1,
                        placeholdersEnabled = false
                    )
                ), expected = PagingSource.LoadResult.Error(ioException)
            )
        }
    }

    @Test
    fun `when github service throw http exception, then same exception should return as load result`() {
        runBlockingTest {

            val httpException = HttpException(
                Response.error<GithubUser>(
                    500, "Test Server Error"
                        .toResponseBody("text/plain".toMediaTypeOrNull())
                )
            )

            coEvery {
                githubService.getPaginatedUser(any(), any())
            } throws httpException

            assertEquals(
                actual = githubPagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 2,
                        placeholdersEnabled = false
                    )
                ), expected = PagingSource.LoadResult.Error(httpException)
            )
        }
    }

    @Test
    fun `when github service response code is 403, then return error response with same message`() {
        runBlockingTest {
            val res = Response.error<ApiUserSearchResponse>(
                403, "some invalid json"
                    .toResponseBody("application/json".toMediaTypeOrNull())
            )
            val exception = Exception("some invalid json")

            coEvery {
                githubService.getPaginatedUser(any(), any())
            } returns res

            assertEquals(
                actual = githubPagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 3,
                        placeholdersEnabled = false
                    )
                ).toString(),
                expected = PagingSource.LoadResult.Error<Int, GithubUser>(exception).toString()
            )
        }
    }

    @Test
    fun `when github service response code is 200, then return mocked data items`() {
        runBlockingTest {
            val res = Response.success<ApiUserSearchResponse>(apiUserSearchResponse)

            every {
                apiUserSearchResponse.items
            } returns userList

            coEvery {
                githubService.getPaginatedUser(any(), any())
            } returns res

            assertEquals(
                actual = githubPagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 4,
                        placeholdersEnabled = false
                    )
                ),
                expected = PagingSource.LoadResult.Page(
                    data = userList,
                    prevKey = null,
                    nextKey = 2
                )
            )
        }
    }
}