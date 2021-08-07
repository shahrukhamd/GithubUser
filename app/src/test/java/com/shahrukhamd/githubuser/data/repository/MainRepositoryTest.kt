package com.shahrukhamd.githubuser.data.repository

import com.shahrukhamd.githubuser.data.api.GithubService
import com.shahrukhamd.githubuser.data.source.GithubPagingSource
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before

class MainRepositoryTest {

    private lateinit var mainRepository: MainRepository

    @MockK
    private lateinit var githubService: GithubService
    
    @MockK
    private lateinit var pagingSourceFactory: GithubPagingSource

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        mainRepository = MainRepository(githubService)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    // TODO not yet anything to test in repository as it's calling the paging source but add tests when adding more business logic
}