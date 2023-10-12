/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.ui.search.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shahrukhamd.githubuser.R
import com.shahrukhamd.githubuser.utils.NetworkStateLiveData
import com.shahrukhamd.githubuser.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NetworkStateLiveData(applicationContext).observe(this, {
            if (!it) {
                showToast(R.string.network_unavailable)
            }
        })
    }
}