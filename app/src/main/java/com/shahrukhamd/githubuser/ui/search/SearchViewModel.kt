/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shahrukhamd.githubuser.data.model.GithubUser
import com.shahrukhamd.githubuser.data.repository.SearchRepository
import com.shahrukhamd.githubuser.di.module.DispatcherModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @DispatcherModule.MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var isShowingStarredUser = false
    private var lastSearchedQuery = "john" // initial search query to fill the list

    private val _searchResponse = MutableStateFlow<PagingData<GithubUser>>(PagingData.empty())
    val searchResponse = _searchResponse.asStateFlow()

    private val _showRefreshingView = MutableStateFlow(false)
    val showRefreshingView = _showRefreshingView.asStateFlow()

    private val _showRetryButton = MutableStateFlow(false)
    val showRetryButton = _showRetryButton.asStateFlow()

    private val _showStarredUserButton = MutableStateFlow(false)
    val showStarredUserButton = _showStarredUserButton.asStateFlow()

    private val _showToast = MutableSharedFlow<String>()
    val showToast = _showToast.asSharedFlow()

    private val _showUserDetails = MutableSharedFlow<Boolean>()
    val showUserDetails = _showUserDetails.asSharedFlow()

    private val _onUserShare = MutableSharedFlow<String>()
    val onUserShare = _onUserShare.asSharedFlow()

    private val _onUserProfileOpen = MutableSharedFlow<String>()
    val onUserProfileOpen = _onUserProfileOpen.asSharedFlow()

    private val _onCloseProfileDetails = MutableSharedFlow<Boolean>()
    val onCloseProfileDetails = _onCloseProfileDetails.asSharedFlow()

    private val _onUserDetailUpdate = MutableLiveData<Pair<Int, GithubUser>>()
    val onUserDetailUpdate: LiveData<Pair<Int, GithubUser>> = _onUserDetailUpdate

    init {
        // todo remove this logic and implement view for when there's no query
        onSearchQueryChanged(lastSearchedQuery)
    }

    fun onSearchQueryChanged(query: String) {
        lastSearchedQuery = query
        viewModelScope.launch(ioDispatcher) {
            searchRepository.getPaginatedUser(query).cachedIn(this).collectLatest {
                _searchResponse.value = it
            }
        }
    }

    fun onUserListLoadStateChange(loadState: CombinedLoadStates) {
        _showRefreshingView.value = loadState.source.refresh is LoadState.Loading
        _showRetryButton.value = loadState.source.refresh is LoadState.Error
        _showStarredUserButton.value = loadState.source.refresh is LoadState.Error

        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error
            ?: loadState.prepend as? LoadState.Error
            ?: loadState.source.refresh as? LoadState.Error

        errorState?.let { error ->
            viewModelScope.launch(mainDispatcher) {
                _showToast.emit(error.error.localizedMessage.orEmpty())
            }
        }
    }

    fun getDetailScreenUserDetails(testUser: GithubUser?) {
        if (_onUserDetailUpdate.value == null && testUser != null) {
            // only for testing purpose at the moment, this will help pass the user login name so this
            // live data initialise with it and the UI testing can be done against the sample user data
            _onUserDetailUpdate.value = Pair(0, testUser)
        }
        _onUserDetailUpdate.value?.let {
            viewModelScope.launch(ioDispatcher) {
                searchRepository.getUserDetailsAndUpdateDb(it.second.login.orEmpty())
                    ?.let { userUpdate ->
                        _onUserDetailUpdate.postValue(Pair(it.first, it.second.copy(userUpdate)))
                    }
            }
        }
    }

    fun onUserListItemClicked(user: GithubUser, position: Int) {
        viewModelScope.launch(mainDispatcher) {
            _showUserDetails.emit(true)
        }
        _onUserDetailUpdate.value = Pair(position, user)
    }

    fun onUserProfileShareClick() {
        _onUserDetailUpdate.value?.second?.htmlUrl?.let {url ->
            viewModelScope.launch(mainDispatcher) {
                _onUserShare.emit(url)
            }
        }
    }

    fun onUserProfileOpenClick() {
        _onUserDetailUpdate.value?.second?.htmlUrl?.let {url ->
            viewModelScope.launch(mainDispatcher) {
                _onUserProfileOpen.emit(url)
            }
        }
    }

    fun onProfileCloseClick() {
        viewModelScope.launch(mainDispatcher) {
            _onCloseProfileDetails.emit(true)
        }
    }

    fun onUserStarClick(position: Int, user: GithubUser) {
        viewModelScope.launch(ioDispatcher) {
            user.isUserStared = !user.isUserStared
            searchRepository.updateUser(user)
            _onUserDetailUpdate.postValue(Pair(position, user))
        }
    }

    fun onStarredUsersClick() {
        isShowingStarredUser = true
        viewModelScope.launch(ioDispatcher) {
            searchRepository.getPagingStarredUsers().cachedIn(this).collectLatest {
                _searchResponse.value = it
            }
        }
    }

    fun onUserRefreshList() {
        if (isShowingStarredUser) {
            isShowingStarredUser = false
            onSearchQueryChanged(lastSearchedQuery)
        }
    }
}