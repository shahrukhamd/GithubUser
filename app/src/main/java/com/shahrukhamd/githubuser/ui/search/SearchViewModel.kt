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
import com.shahrukhamd.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private var searchRepository: SearchRepository) : ViewModel() {

    private val _searchResponse = MutableLiveData<PagingData<GithubUser>>()
    val searchResponse: LiveData<PagingData<GithubUser>> = _searchResponse

    private val _showRefreshingView = MutableLiveData<Event<Boolean>>()
    val showRefreshingView: LiveData<Event<Boolean>> = _showRefreshingView

    private val _showRetryButton = MutableLiveData<Event<Boolean>>()
    val showRetryButton: LiveData<Event<Boolean>> = _showRetryButton

    private val _showToast = MutableLiveData<Event<String>>()
    val showToast: LiveData<Event<String>> = _showToast

    private val _showUserDetails = MutableLiveData<Event<Boolean>>()
    val showUserDetails: LiveData<Event<Boolean>> = _showUserDetails

    private val _onUserShare = MutableLiveData<Event<String>>()
    val onUserShare: LiveData<Event<String>> = _onUserShare

    private val _onUserProfileOpen = MutableLiveData<Event<String>>()
    val onUserProfileOpen: LiveData<Event<String>> = _onUserProfileOpen

    private val _onCloseProfileDetails = MutableLiveData<Event<Boolean>>()
    val onCloseProfileDetails: LiveData<Event<Boolean>> = _onCloseProfileDetails

    private val _onUserDetailUpdate = MutableLiveData<Pair<Int, GithubUser>>()
    val onUserDetailUpdate: LiveData<Pair<Int, GithubUser>> = _onUserDetailUpdate

    init {
        // todo remove this logic and implement view for when there's no query
        onSearchQueryChanged("john") // initial search query to fill the list
    }

    fun onSearchQueryChanged(query: String) {
        viewModelScope.launch {
            searchRepository.getPaginatedUser(query).cachedIn(this).collectLatest {
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

    fun getDetailScreenUserDetails(testUser: GithubUser?) {
        if (_onUserDetailUpdate.value == null && testUser != null) {
            // only for testing purpose at the moment, this will help pass the user login name so this
            // live data initialise with it and the UI testing can be done against the sample user data
            _onUserDetailUpdate.value = Pair(0, testUser)
        }
        _onUserDetailUpdate.value?.let {
            viewModelScope.launch {
                searchRepository.getUserDetailsAndUpdateDb(it.second.login.orEmpty())?.let {
                    userUpdate -> _onUserDetailUpdate.value = Pair(it.first, it.second.copy(userUpdate))
                }
            }
        }
    }

    fun onUserListItemClicked(user: GithubUser, position: Int) {
        _showUserDetails.value = Event(true)
        _onUserDetailUpdate.value = Pair(position, user)
    }

    fun onUserProfileShareClick() {
        _onUserDetailUpdate.value?.second?.htmlUrl?.let {
            _onUserShare.value = Event(it)
        }
    }

    fun onUserProfileOpenClick() {
        _onUserDetailUpdate.value?.second?.htmlUrl?.let {
            _onUserProfileOpen.value = Event(it)
        }
    }

    fun onProfileCloseClick() {
        _onCloseProfileDetails.value = Event(true)
    }

    fun onUserStarClick(position: Int, user: GithubUser) {
        viewModelScope.launch {
            user.isUserStared = !user.isUserStared
            searchRepository.updateUser(user)
            _onUserDetailUpdate.postValue(Pair(position, user))
        }
    }
}