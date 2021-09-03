/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.ui.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.shahrukhamd.githubuser.databinding.FragmentUserDetailsBinding
import com.shahrukhamd.githubuser.utils.EventObserver
import com.shahrukhamd.githubuser.utils.showToast

class UserDetailFragment: Fragment() {

    private val viewModel: SearchViewModel by activityViewModels()

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
        viewModel.getDetailScreenUserDetails()
    }

    private fun initViews() {
        viewModel.onUserShare.observe(viewLifecycleOwner, EventObserver {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, it)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        })

        viewModel.onUserProfileOpen.observe(viewLifecycleOwner, EventObserver {
            val openIntent: Intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(it)
            }
            startActivity(openIntent)
        })
    }

    private fun initObserver() {
        viewModel.onCloseProfileDetails.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigateUp()
        })

        viewModel.showToast.observe(viewLifecycleOwner, EventObserver {
            context?.showToast(it)
        })
    }
}