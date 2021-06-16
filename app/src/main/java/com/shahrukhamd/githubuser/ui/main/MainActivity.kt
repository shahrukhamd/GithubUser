/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.shahrukhamd.githubuser.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private var userListAdapter: UserListRecyclerViewAdapter? = null
    private var viewBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        viewBinding?.viewModel = mainViewModel
        setContentView(viewBinding?.root)
        initViews()

        mainViewModel.onSearchQueryChanged("shahrukh")
    }

    private fun initViews() {
        userListAdapter = UserListRecyclerViewAdapter()
        viewBinding?.userListRecyclerView?.adapter = userListAdapter

        mainViewModel.searchResponse.observe(this, {
            viewBinding?.swipeRefresh?.isRefreshing = false
            lifecycleScope.launch { userListAdapter?.submitData(it) }
        })
    }
}