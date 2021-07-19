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
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shahrukhamd.githubuser.data.model.GithubUser
import com.shahrukhamd.githubuser.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private var mainRepository: MainRepository): ViewModel() {

    private val _searchResponse = MutableLiveData<PagingData<GithubUser>>()
    val searchResponse: LiveData<PagingData<GithubUser>> = _searchResponse

    fun onSearchQueryChanged(query: String) {
        viewModelScope.launch {
            mainRepository.getPaginatedUser(query).cachedIn(this).collectLatest {
                _searchResponse.postValue(it)
            }
        }
    }
}