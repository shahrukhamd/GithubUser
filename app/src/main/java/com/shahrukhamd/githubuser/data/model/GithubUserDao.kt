/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * The Data Access Object for the [GithubUser] class.
 */
@Dao
interface GithubUserDao {

    @Query("SELECT * FROM github_user WHERE isUserStared = 1")
    suspend fun getStaredUsers(): List<GithubUser>

    @Query("SELECT * FROM github_user WHERE id = :id")
    suspend fun getUser(id: Int): GithubUser?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: GithubUser)
}