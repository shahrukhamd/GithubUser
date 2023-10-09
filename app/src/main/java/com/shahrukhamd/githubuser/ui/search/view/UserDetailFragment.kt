/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.ui.search.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shahrukhamd.githubuser.databinding.FragmentUserDetailsBinding
import com.shahrukhamd.githubuser.ui.search.SearchViewModel
import com.shahrukhamd.githubuser.utils.launchAndCollectIn
import com.shahrukhamd.githubuser.utils.showToast

class UserDetailFragment : Fragment() {

    private val viewModel: SearchViewModel by activityViewModels()

    private val args: UserDetailFragmentArgs by navArgs()

    private lateinit var viewBinding: FragmentUserDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentUserDetailsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@UserDetailFragment.viewModel
        }
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObserver()
        viewModel.getDetailScreenUserDetails(args.user)
    }

    private fun initViews() {
        viewModel.onUserShare.launchAndCollectIn(viewLifecycleOwner) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, it)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        viewModel.onUserProfileOpen.launchAndCollectIn(viewLifecycleOwner) {
            val openIntent: Intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(it)
            }
            startActivity(openIntent)
        }
    }

    private fun initObserver() {
        viewModel.onCloseProfileDetails.launchAndCollectIn(viewLifecycleOwner) {
            if (it) {
                findNavController().navigateUp()
            }
        }

        viewModel.showToast.launchAndCollectIn(viewLifecycleOwner) {
            context?.showToast(it)
        }
    }
}