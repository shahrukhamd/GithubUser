/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.ui.main

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.shahrukhamd.githubuser.R
import com.shahrukhamd.githubuser.databinding.ActivityMainBinding
import com.shahrukhamd.githubuser.ui.common.ListItemLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private var userListAdapter: UserListRecyclerViewAdapter? = null
    private lateinit var viewBinding: ActivityMainBinding

    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        viewBinding.viewModel = mainViewModel
        setContentView(viewBinding.root)
        initViews()

        if (savedInstanceState == null) {
            mainViewModel.onSearchQueryChanged("john") //initial search query to fill the list
        }
    }

    private fun initViews() {
        userListAdapter = UserListRecyclerViewAdapter()
        viewBinding.rvUserList.adapter =
            userListAdapter?.withLoadStateFooter(ListItemLoadStateAdapter { userListAdapter?.retry() })

        userListAdapter?.addLoadStateListener { mainViewModel.onUserListLoadStateChange(it) }

        viewBinding.btnRetry.setOnClickListener { userListAdapter?.refresh() }
        viewBinding.swipeRefresh.setOnRefreshListener { userListAdapter?.refresh() }

        mainViewModel.searchResponse.observe(this, {
            viewBinding.swipeRefresh.isRefreshing = false
            lifecycleScope.launch { userListAdapter?.submitData(it) }
        })

        mainViewModel.showToast.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.user_search_option, menu)
        val searchItem = menu?.findItem(R.id.user_search)
        searchView = searchItem?.actionView as? SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.trim().isEmpty().not()) {
                    mainViewModel.onSearchQueryChanged(query.trim())
                    searchView?.clearFocus()
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return true
    }
}