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
import com.shahrukhamd.githubuser.data.model.ApiUserSearchResponse
import com.shahrukhamd.githubuser.data.repository.MainRepository
import com.shahrukhamd.githubuser.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var mainRepository: MainRepository): ViewModel() {

    private val _searchResponse = MutableLiveData<Resource<ApiUserSearchResponse?>>()
    val searchResponse: LiveData<Resource<ApiUserSearchResponse?>> = _searchResponse

    fun onSearchQueryChanged(query: String) {
        viewModelScope.launch {
            _searchResponse.value = Resource.loading()
            mainRepository.getUser(query).let {
                if (it.isSuccessful) {
                    _searchResponse.value = Resource.success(it.body())
                } else {
                    _searchResponse.value = Resource.error(it.body(), it.message())
                }
            }
        }


    }
}