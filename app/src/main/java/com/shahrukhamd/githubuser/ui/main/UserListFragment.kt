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
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.shahrukhamd.githubuser.databinding.FragmentUserListBinding
import com.shahrukhamd.githubuser.ui.common.ListItemLoadStateAdapter
import com.shahrukhamd.githubuser.utils.EventObserver
import com.shahrukhamd.githubuser.utils.showToast
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
        viewBinding.lifecycleOwner = viewLifecycleOwner
        viewBinding.viewModel = viewModel

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        userListAdapter = UserListRecyclerViewAdapter { user ->
            user?.let { viewModel.onUserListItemClicked(it) }
        }

        viewBinding.rvUserList.adapter =
            userListAdapter?.withLoadStateFooter(ListItemLoadStateAdapter { userListAdapter?.retry() })

        userListAdapter?.addLoadStateListener { viewModel.onUserListLoadStateChange(it) }

        viewBinding.btnRetry.setOnClickListener { userListAdapter?.refresh() }
        viewBinding.swipeRefresh.setOnRefreshListener { userListAdapter?.refresh() }

        viewModel.searchResponse.observe(viewLifecycleOwner, {
            viewBinding.swipeRefresh.isRefreshing = false
            lifecycleScope.launch { userListAdapter?.submitData(it) }
        })

        viewModel.showToast.observe(viewLifecycleOwner, EventObserver {
            context?.showToast(it)
        })

        viewModel.navigateToUserDetail.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(
                UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(it)
            )
        })
    }
}