/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shahrukhamd.githubuser.data.model.GithubUser
import com.shahrukhamd.githubuser.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private var mainRepository: MainRepository) : ViewModel() {

    private val _searchResponse = MutableLiveData<PagingData<GithubUser>>()
    val searchResponse: LiveData<PagingData<GithubUser>> = _searchResponse

    private val _showRefreshingView = MutableLiveData<Boolean>()
    val showRefreshingView: LiveData<Boolean> = _showRefreshingView

    private val _showRetryButton = MutableLiveData<Boolean>()
    val showRetryButton: LiveData<Boolean> = _showRetryButton

    private val _showToast = MutableLiveData<String>()
    val showToast: LiveData<String> = _showToast

    private val _navigateToUserDetail = MutableLiveData<GithubUser>()
    val navigateToUserDetail: LiveData<GithubUser> = _navigateToUserDetail

    init {
        // todo remove this logic and implement view for when there's no query
        onSearchQueryChanged("john") // initial search query to fill the list
    }

    fun onSearchQueryChanged(query: String) {
        viewModelScope.launch {
            mainRepository.getPaginatedUser(query).cachedIn(this).collectLatest {
                _searchResponse.postValue(it)
            }
        }
    }

    fun onUserListLoadStateChange(loadState: CombinedLoadStates) {
        _showRefreshingView.value = loadState.source.refresh is LoadState.Loading
        _showRetryButton.value = loadState.source.refresh is LoadState.Error

        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error
            ?: loadState.prepend as? LoadState.Error
            ?: loadState.source.refresh as? LoadState.Error

        errorState?.let { _showToast.value = it.error.localizedMessage }
    }

    fun onUserListItemClicked(user: GithubUser) {
        _navigateToUserDetail.value = user
    }
}