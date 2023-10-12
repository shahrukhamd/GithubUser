/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.google.common.truth.Truth.assertThat
import com.shahrukhamd.githubuser.MainDispatcherRule
import com.shahrukhamd.githubuser.data.model.GithubUser
import com.shahrukhamd.githubuser.data.repository.SearchRepository
import com.shahrukhamd.githubuser.ui.search.SearchViewModel
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var combinedLoadStates: CombinedLoadStates

    @MockK
    private lateinit var searchRepository: SearchRepository

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = SearchViewModel(
            searchRepository,
            mainDispatcherRule.testDispatcher,
            mainDispatcherRule.testDispatcher
        )
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `when user list load state is loading, then show refreshing view and hide retry view`() {
        every { combinedLoadStates.source.refresh } returns LoadState.Loading

        viewModel.onUserListLoadStateChange(combinedLoadStates)

        assertThat(viewModel.showRefreshingView.value).isTrue()
        assertThat(viewModel.showRetryButton.value).isFalse()
    }

    @Test
    fun `when user list load state is error, then show retry view and hide refresh view`() {
        every { combinedLoadStates.source.refresh } returns LoadState.Error(Exception())

        viewModel.onUserListLoadStateChange(combinedLoadStates)

        assertThat(viewModel.showRefreshingView.value).isFalse()
        assertThat(viewModel.showRetryButton.value).isTrue()
    }

    @Test
    fun `when called search query changes, repository api should be called with same query`() =
        runTest {
            viewModel.onSearchQueryChanged("some query")

            verify { searchRepository.getPaginatedUser("some query") }
        }

    @Test
    fun `when fetch details for user, repository api should be called with same login`() = runTest {
        val user = GithubUser(id = 1, login = "username")

        viewModel.getDetailScreenUserDetails(user)

        coVerify {
            searchRepository.getUserDetailsAndUpdateDb("username")
        }
    }

    @Test
    fun `when user star clicked, update user profile in repository`() = runTest {
        val user = GithubUser(id = 1, htmlUrl = "htmlUrl")

        viewModel.onUserStarClick(1, user)

        coVerify {
            searchRepository.updateUser(user)
        }
    }

    @Test
    fun `when starred users clicked, paginated starred users should be fetched from repository`() =
        runTest {
            viewModel.onStarredUsersClick()

            coVerify {
                searchRepository.getPagingStarredUsers()
            }
        }

    @Test
    fun `when user refresh list, repository should be called to fetch paginated users`() = runTest {
        viewModel.onUserRefreshList()

        coVerify {
            searchRepository.getPaginatedUser(any())
        }
    }
}