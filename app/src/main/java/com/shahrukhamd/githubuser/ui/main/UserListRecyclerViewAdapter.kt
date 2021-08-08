package com.shahrukhamd.githubuser.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shahrukhamd.githubuser.R
import com.shahrukhamd.githubuser.data.model.GithubUser
import com.shahrukhamd.githubuser.databinding.ViewUserListItemBinding
import com.shahrukhamd.githubuser.utils.listen

class UserListRecyclerViewAdapter(private val clickListener: (GithubUser?) -> Unit) :
    PagingDataAdapter<GithubUser, UserListRecyclerViewAdapter.ViewHolder>(UserListDiffUtil()) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ViewUserListItemBinding? = DataBindingUtil.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_user_list_item, parent, false)

        return ViewHolder(view).listen { position, _ ->
            clickListener.invoke(getItem(position))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding?.userModel = item
    }
}

class UserListDiffUtil : DiffUtil.ItemCallback<GithubUser>() {
    override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
        return oldItem == newItem
    }
}