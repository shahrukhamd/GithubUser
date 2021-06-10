/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.shahrukhamd.githubuser.databinding.ActivityMainBinding
import com.shahrukhamd.githubuser.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private var userListAdapter: MainViewListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityMainBinding.inflate(layoutInflater)
        viewBinding.viewModel = mainViewModel
        setContentView(viewBinding.root)
        userListAdapter = MainViewListAdapter()
        viewBinding.userListRecyclerView.adapter = userListAdapter

        mainViewModel.searchResponse.observe(this, {
            viewBinding.swipeRefresh.isRefreshing = false
            when(it.status) {
                Status.Success -> {
                    userListAdapter?.postData(it.data?.items)
                }
                Status.Loading -> {
                    viewBinding.swipeRefresh.isRefreshing = true
                }
                Status.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        mainViewModel.onSearchQueryChanged("shahrukh")
    }
}