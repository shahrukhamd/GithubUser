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
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.shahrukhamd.githubuser.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private var searchView: SearchView? = null
    private lateinit var mainNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        mainNavController = navHostFragment.navController

        viewModel.navigateToUserDetail.observe(this, {
            // todo check why on screen rotation this is getting called again
            if (mainNavController.currentDestination?.id != R.id.userDetailFragment) {
                mainNavController.navigate(
                    UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(it)
                )
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.user_search_option, menu)
        val searchItem = menu?.findItem(R.id.user_search)
        searchView = searchItem?.actionView as? SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.trim().isEmpty().not()) {
                    viewModel.onSearchQueryChanged(query.trim())
                    searchView?.clearFocus()
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
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