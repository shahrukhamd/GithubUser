package com.shahrukhamd.githubuser.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.shahrukhamd.githubuser.R
import com.shahrukhamd.githubuser.data.model.User
import com.shahrukhamd.githubuser.databinding.ViewUserListItemBinding

class MainViewListAdapter: RecyclerView.Adapter<MainViewListAdapter.ViewHolder>() {

    private var userList: MutableList<User> = mutableListOf()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ViewUserListItemBinding? = DataBindingUtil.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_user_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding?.userModel = userList[position]
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun postData(data: List<User>?) {
        data?.let {
            userList.clear()
            userList.addAll(it)
            notifyDataSetChanged()
        }
    }
}