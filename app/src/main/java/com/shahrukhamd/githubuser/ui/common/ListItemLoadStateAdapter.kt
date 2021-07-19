package com.shahrukhamd.githubuser.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.shahrukhamd.githubuser.R
import com.shahrukhamd.githubuser.databinding.ViewListItemFooterLoadStateBinding

class ListItemLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<ListItemFooterViewHolder>() {
    override fun onBindViewHolder(holder: ListItemFooterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ListItemFooterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_list_item_footer_load_state, parent, false)
        val binding = ViewListItemFooterLoadStateBinding.bind(view)
        return ListItemFooterViewHolder(binding, retry)
    }
}