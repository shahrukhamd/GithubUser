/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.shahrukhamd.githubuser.R
import com.shahrukhamd.githubuser.databinding.FragmentUserDetailsBinding
import com.shahrukhamd.githubuser.utils.EventObserver

class UserDetailFragment: Fragment(R.layout.fragment_user_details) {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var viewBinding: FragmentUserDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        viewBinding.lifecycleOwner = viewLifecycleOwner
        viewBinding.viewModel = viewModel
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.getCurrentUserDetails()
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
}