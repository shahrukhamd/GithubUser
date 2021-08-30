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
import com.shahrukhamd.githubuser.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private var mainRepository: MainRepository) : ViewModel() {

    private val _searchResponse = MutableLiveData<PagingData<GithubUser>>()
    val searchResponse: LiveData<PagingData<GithubUser>> = _searchResponse

    private val _showRefreshingView = MutableLiveData<Event<Boolean>>()
    val showRefreshingView: LiveData<Event<Boolean>> = _showRefreshingView

    private val _showRetryButton = MutableLiveData<Event<Boolean>>()
    val showRetryButton: LiveData<Event<Boolean>> = _showRetryButton

    private val _showToast = MutableLiveData<Event<String>>()
    val showToast: LiveData<Event<String>> = _showToast

    private val _navigateToUserDetail = MutableLiveData<Event<GithubUser>>()
    val navigateToUserDetail: LiveData<Event<GithubUser>> = _navigateToUserDetail

    private val _userDetailUpdated = MutableLiveData<GithubUser>()
    val userDetailUpdated: LiveData<GithubUser> = _userDetailUpdated

    private val _onUserShare = MutableLiveData<Event<String>>()
    val onUserShare: LiveData<Event<String>> = _onUserShare

    private val _onUserProfileOpen = MutableLiveData<Event<String>>()
    val onUserProfileOpen: LiveData<Event<String>> = _onUserProfileOpen

    private val _onCloseProfileDetails = MutableLiveData<Event<Boolean>>()
    val onCloseProfileDetails: LiveData<Event<Boolean>> = _onCloseProfileDetails

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
        _showRefreshingView.value = Event(loadState.source.refresh is LoadState.Loading)
        _showRetryButton.value = Event(loadState.source.refresh is LoadState.Error)

        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error
            ?: loadState.prepend as? LoadState.Error
            ?: loadState.source.refresh as? LoadState.Error

        errorState?.let { _showToast.value = Event(it.error.localizedMessage) }
    }

    fun getCurrentUserDetails() {
        // todo refactor this to show loading, error and other scenarios
        val userName = _userDetailUpdated.value?.login
        userName?.let {
            viewModelScope.launch {
                mainRepository.getUserDetails(it)?.let {
                    _userDetailUpdated.value = it
                }
            }
        }
    }

    fun onUserListItemClicked(user: GithubUser) {
        _navigateToUserDetail.value = Event(user)
        _userDetailUpdated.value = user
    }

    fun onUserShareButtonClick() {
        _userDetailUpdated.value?.htmlUrl?.let {
            _onUserShare.value = Event(it)
        }
    }

    fun onUserProfileOpenButtonClick() {
        _userDetailUpdated.value?.htmlUrl?.let {
            _onUserProfileOpen.value = Event(it)
        }
    }

    fun onProfileCloseClick() {
        _onCloseProfileDetails.value = Event(true)
    }
}