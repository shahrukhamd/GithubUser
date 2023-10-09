/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.ui.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.shahrukhamd.githubuser.databinding.FragmentUserListBinding
import com.shahrukhamd.githubuser.ui.common.ListItemLoadStateAdapter
import com.shahrukhamd.githubuser.ui.common.isVisible
import com.shahrukhamd.githubuser.ui.search.SearchViewModel
import com.shahrukhamd.githubuser.ui.search.UserListRecyclerViewAdapter
import com.shahrukhamd.githubuser.utils.DebouncingQueryTextListener
import com.shahrukhamd.githubuser.utils.launchAndCollectIn
import com.shahrukhamd.githubuser.utils.showToast
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class UserListFragment : Fragment() {

    private val viewModel: SearchViewModel by activityViewModels()

    private lateinit var viewBinding: FragmentUserListBinding

    private var userListAdapter: UserListRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentUserListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@UserListFragment.viewModel
        }
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObserver()
    }

    private fun initViews() {
        userListAdapter = UserListRecyclerViewAdapter(viewModel)

        viewBinding.rvUserList.adapter =
            userListAdapter?.withLoadStateFooter(ListItemLoadStateAdapter { userListAdapter?.retry() })

        userListAdapter?.addLoadStateListener { viewModel.onUserListLoadStateChange(it) }

        viewBinding.btnRetry.setOnClickListener { onUserRefreshList() }
        viewBinding.swipeRefresh.setOnRefreshListener { onUserRefreshList() }

        viewBinding.svUserSearch.setOnQueryTextListener(
            DebouncingQueryTextListener(lifecycle) {
                if (it.isNotEmpty()) {
                    viewModel.onSearchQueryChanged(it.trim())
                }
            }
        )
    }

    private fun initObserver() {
        viewModel.searchResponse.launchAndCollectIn(viewLifecycleOwner) {
            viewBinding.swipeRefresh.isRefreshing = false
            lifecycleScope.launch { userListAdapter?.submitData(it) }
        }

        viewModel.showRefreshingView.launchAndCollectIn(viewLifecycleOwner) {
            viewBinding.swipeRefresh.isRefreshing = it
        }

        viewModel.showRetryButton.launchAndCollectIn(viewLifecycleOwner) {
            viewBinding.btnRetry.isVisible(it)
        }

        viewModel.showStarredUserButton.launchAndCollectIn(viewLifecycleOwner) {
            viewBinding.btnShowStarredUsers.isVisible(it)
        }

        viewModel.showToast.launchAndCollectIn(viewLifecycleOwner) {
            context?.showToast(it)
        }

        viewModel.showUserDetails.launchAndCollectIn(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(
                    UserListFragmentDirections.actionUserListFragmentToUserDetailFragment()
                )
            }
        }

        viewModel.onUserDetailUpdate.observe(viewLifecycleOwner, {
            lifecycleScope.launch {
                userListAdapter?.notifyItemChanged(it.first, it.second)
            }
        })
    }

    private fun onUserRefreshList() {
        userListAdapter?.refresh()
        viewModel.onUserRefreshList()
    }
}