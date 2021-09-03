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

	@Json(name="gists_url")
	var gistsUrl: String? = null,

	@Json(name="repos_url")
	var reposUrl: String? = null,

	@Json(name="following_url")
	var followingUrl: String? = null,

	@Json(name="twitter_username")
	var twitterUsername: String? = null,

	@Json(name="bio")
	var bio: String? = null,

	@Json(name="created_at")
	var createdAt: String? = null,

	@Json(name="login")
	var login: String? = null,

	@Json(name="type")
	var type: String? = null,

	@Json(name="blog")
	var blog: String? = null,

	@Json(name="subscriptions_url")
	var subscriptionsUrl: String? = null,

	@Json(name="updated_at")
	var updatedAt: String? = null,

	@Json(name="site_admin")
	var siteAdmin: Boolean? = null,

	@Json(name="company")
	var company: String? = null,

	@PrimaryKey
	@Json(name="id")
	var id: Int,

	@Json(name="public_repos")
	var publicRepos: Int? = null,

	@Json(name="gravatar_id")
	var gravatarId: String? = null,

	@Json(name="email")
	var email: String? = null,

	@Json(name="organizations_url")
	var organizationsUrl: String? = null,

	@Json(name="hireable")
	var hireable: Boolean? = null,

	@Json(name="starred_url")
	var starredUrl: String? = null,

	@Json(name="followers_url")
	var followersUrl: String? = null,

	@Json(name="public_gists")
	var publicGists: Int? = null,

	@Json(name="url")
	var url: String? = null,

	@Json(name="received_events_url")
	var receivedEventsUrl: String? = null,

	@Json(name="followers")
	var followers: Int? = null,

	@Json(name="avatar_url")
	var avatarUrl: String? = null,

	@Json(name="events_url")
	var eventsUrl: String? = null,

	@Json(name="html_url")
	var htmlUrl: String? = null,

	@Json(name="following")
	var following: Int? = null,

	@Json(name="name")
	var name: String? = null,

	@Json(name="location")
	var location: String? = null,

	@Json(name="node_id")
	var nodeId: String? = null,

	var isUserStared: Boolean = false

) : Parcelable {
	fun copy(new: GithubUser?): GithubUser {
		new?.let { newUser ->
			id = newUser.id
			gistsUrl = newUser.gistsUrl
			reposUrl = newUser.reposUrl
			followingUrl = newUser.followingUrl
			twitterUsername = newUser.twitterUsername
			bio = newUser.bio
			createdAt = newUser.createdAt
			login = newUser.login
			type = newUser.type
			blog = newUser.blog
			subscriptionsUrl = newUser.subscriptionsUrl
			updatedAt = newUser.updatedAt
			siteAdmin = newUser.siteAdmin
			company = newUser.company
			publicRepos = newUser.publicRepos
			gravatarId = newUser.gravatarId
			email = newUser.email
			organizationsUrl = newUser.organizationsUrl
			hireable = newUser.hireable
			starredUrl = newUser.starredUrl
			followersUrl = newUser.followersUrl
			publicGists = newUser.publicGists
			url = newUser.url
			receivedEventsUrl = newUser.receivedEventsUrl
			followers = newUser.followers
			avatarUrl = newUser.avatarUrl
			eventsUrl = newUser.eventsUrl
			htmlUrl = newUser.htmlUrl
			following = newUser.following
			name = newUser.name
			location = newUser.location
			nodeId = newUser.nodeId
//			isUserStared = newUser.isUserStared	// remote user object shouldn't update local star value
		}
		return this
	}
}