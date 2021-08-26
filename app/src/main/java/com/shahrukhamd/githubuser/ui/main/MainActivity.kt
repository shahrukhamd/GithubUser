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
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.shahrukhamd.githubuser.R
import com.shahrukhamd.githubuser.utils.DebouncingQueryTextListener
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

        viewModel.onUserShare.observe(this, {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, it)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        })

        viewModel.onUserProfileOpen.observe(this, {
            val openIntent: Intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(it)
            }
            startActivity(openIntent)
        })
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