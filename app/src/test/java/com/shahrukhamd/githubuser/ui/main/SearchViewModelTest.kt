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
import com.shahrukhamd.githubuser.data.repository.SearchRepository
import com.shahrukhamd.githubuser.ui.search.SearchViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    // Run tasks synchronously
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var combinedLoadStates: CombinedLoadStates

    @MockK
    private lateinit var searchRepository: SearchRepository

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = SearchViewModel(searchRepository)
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
    fun `when user list load state is error without message, then show toast without message`() {
        every { combinedLoadStates.source.refresh } returns LoadState.Error(Exception())

        viewModel.onUserListLoadStateChange(combinedLoadStates)

        assertThat(viewModel.showToast.value).isNull()
    }

    @Test
    fun `when user list load state is error with message, then show toast with same message`() {
        every { combinedLoadStates.source.refresh } returns LoadState.Error(Exception("some message"))

        viewModel.onUserListLoadStateChange(combinedLoadStates)

        assertThat(viewModel.showToast.value).matches("some message")
    }

    @Test
    fun `when called search query changes, repository api should be called with same query`() {
        viewModel.onSearchQueryChanged("some query")

        verify { searchRepository.getPaginatedUser("some query") }
    }
}