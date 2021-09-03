/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.ui.common

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.shahrukhamd.githubuser.databinding.ViewListItemFooterLoadStateBinding

class ListItemFooterViewHolder(
    private val binding: ViewListItemFooterLoadStateBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.listItemFooterRetryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.listItemFooterText.text = loadState.error.localizedMessage
        }

        binding.listItemFooterProgressBar.isVisible = loadState is LoadState.Loading
        binding.listItemFooterRetryButton.isVisible = loadState is LoadState.Error
        binding.listItemFooterText.isVisible = loadState is LoadState.Error
    }
}