/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.data.repository

import com.shahrukhamd.githubuser.data.api.GithubService
import com.shahrukhamd.githubuser.data.source.GithubPagingSource
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before

class SearchRepositoryTest {

    private lateinit var searchRepository: SearchRepository

    @MockK
    private lateinit var githubService: GithubService
    
    @MockK
    private lateinit var pagingSourceFactory: GithubPagingSource

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        searchRepository = SearchRepository(githubService)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    // TODO not yet anything to test in repository as it's calling the paging source but add tests when adding more business logic
}