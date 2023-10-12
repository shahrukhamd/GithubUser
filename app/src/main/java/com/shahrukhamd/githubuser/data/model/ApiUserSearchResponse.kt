/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApiUserSearchResponse(

	@Json(name="total_count")
	var totalCount: Int? = null,

	@Json(name="incomplete_results")
	var incompleteResults: Boolean? = null,

	@Json(name="items")
	var items: List<GithubUser>? = null
) : Parcelable

@Entity(tableName = "github_user")
@Parcelize
data class GithubUser(

	@Json(name="twitter_username")
	var twitterUsername: String? = null,

	@Json(name="bio")
	var bio: String? = null,

	@Json(name="login")
	var login: String? = null,

	@Json(name="blog")
	var blog: String? = null,

	@PrimaryKey
	@Json(name="id")
	var id: Int,

	@Json(name="public_repos")
	var publicRepos: Int? = null,

	@Json(name="email")
	var email: String? = null,

	@Json(name="followers")
	var followers: Int? = null,

	@Json(name="avatar_url")
	var avatarUrl: String? = null,

	@Json(name="html_url")
	var htmlUrl: String? = null,

	@Json(name="following")
	var following: Int? = null,

	@Json(name="name")
	var name: String? = null,

	@Json(name="location")
	var location: String? = null,

	var isUserStared: Boolean = false

) : Parcelable {
	fun copy(new: GithubUser?): GithubUser {
		new?.let { newUser ->
			id = newUser.id
			twitterUsername = newUser.twitterUsername
			bio = newUser.bio
			login = newUser.login
			blog = newUser.blog
			publicRepos = newUser.publicRepos
			email = newUser.email
			followers = newUser.followers
			avatarUrl = newUser.avatarUrl
			htmlUrl = newUser.htmlUrl
			following = newUser.following
			name = newUser.name
			location = newUser.location
//			isUserStared = newUser.isUserStared	// remote user object shouldn't update local star value
		}
		return this
	}
}