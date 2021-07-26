package com.shahrukhamd.githubuser.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.google.common.truth.Truth.assertThat
import com.shahrukhamd.githubuser.data.repository.MainRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    // Run tasks synchronously
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var combinedLoadStates: CombinedLoadStates

    @MockK
    private lateinit var mainRepository: MainRepository

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = MainViewModel(mainRepository)
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

        verify { mainRepository.getPaginatedUser("some query") }
    }
}