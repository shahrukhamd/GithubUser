/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.ui.main

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.shahrukhamd.githubuser.R
import com.shahrukhamd.githubuser.utils.DebouncingQueryTextListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.user_search_option, menu)
        val searchItem = menu?.findItem(R.id.user_search)
        searchView = searchItem?.actionView as? SearchView

        searchView?.setOnQueryTextListener(DebouncingQueryTextListener(lifecycle) {
            if (it.isNotEmpty()) {
                viewModel.onSearchQueryChanged(it.trim())
            }
        })

        return true
    }

    override fun onBackPressed() {
        if (searchView != null && searchView?.isIconified == false) {
            searchView?.onActionViewCollapsed()
        } else {
            super.onBackPressed()
        }
    }
}