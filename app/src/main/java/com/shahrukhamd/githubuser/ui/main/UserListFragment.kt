/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.shahrukhamd.githubuser.databinding.FragmentUserListBinding
import com.shahrukhamd.githubuser.ui.common.ListItemLoadStateAdapter
import kotlinx.coroutines.launch

class UserListFragment: Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var viewBinding: FragmentUserListBinding

    private var userListAdapter: UserListRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentUserListBinding.inflate(inflater, container, false)
        viewBinding.viewModel = viewModel

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        if (savedInstanceState == null) {
            viewModel.onSearchQueryChanged("john") //initial search query to fill the list
        }
    }

    private fun initViews() {
        userListAdapter = UserListRecyclerViewAdapter()
        viewBinding.rvUserList.adapter =
            userListAdapter?.withLoadStateFooter(ListItemLoadStateAdapter { userListAdapter?.retry() })

        userListAdapter?.addLoadStateListener { viewModel.onUserListLoadStateChange(it) }

        viewBinding.btnRetry.setOnClickListener { userListAdapter?.refresh() }
        viewBinding.swipeRefresh.setOnRefreshListener { userListAdapter?.refresh() }

        viewModel.showRefreshingView.observe(viewLifecycleOwner, {
            // todo find out why app:refreshing="@{viewModel.showRefreshingView}" not working
            viewBinding.swipeRefresh.isRefreshing = it
        })

        viewModel.searchResponse.observe(viewLifecycleOwner, {
            viewBinding.swipeRefresh.isRefreshing = false
            lifecycleScope.launch { userListAdapter?.submitData(it) }
        })

        viewModel.showToast.observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }
}