/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser

import com.shahrukhamd.githubuser.data.model.GithubUser

val SampleUserDetailsObj = GithubUser(
    gistsUrl = "https://api.github.com/users/shahrukhamd/gists{/gist_id}",
    reposUrl = "https://api.github.com/users/shahrukhamd/repos",
    followingUrl = "https://api.github.com/users/shahrukhamd/following{/other_user}",
    twitterUsername = "shahrukhamd",
    bio = "Software Engineer @ Lalamove | Android Apps | SDK | Shutterbug | Dreamer | Thinker",
    createdAt = "2014-05-11T13:14:05Z, login=shahrukhamd",
    type = "User",
    blog = "https://shahrukhamd.com/",
    subscriptionsUrl = "https://api.github.com/users/shahrukhamd/subscriptions",
    updatedAt = "2021-09-12T08:04:09Z",
    siteAdmin = false,
    company = "Lalamove",
    id = 7548925,
    publicRepos = 7,
    gravatarId = "",
    email = null,
    organizationsUrl = "https://api.github.com/users/shahrukhamd/orgs",
    hireable = true,
    starredUrl = "https://api.github.com/users/shahrukhamd/starred{/owner}{/repo}",
    followersUrl = "https://api.github.com/users/shahrukhamd/followers",
    publicGists = 2,
    url = "https://api.github.com/users/shahrukhamd",
    receivedEventsUrl = "https://api.github.com/users/shahrukhamd/received_events",
    followers = 4,
    avatarUrl = "https://avatars.githubusercontent.com/u/7548925?v=4",
    eventsUrl = "https://api.github.com/users/shahrukhamd/events{/privacy}",
    htmlUrl = "https://github.com/shahrukhamd",
    following = 0,
    name = "ShahRukh",
    location = "Hong Kong",
    nodeId = "MDQ6VXNlcjc1NDg5MjU=",
    isUserStared = false
)

val SampleUserDetailsJson = """{
   "login":"shahrukhamd",
   "id":7548925,
   "node_id":"MDQ6VXNlcjc1NDg5MjU=",
   "avatar_url":"https://avatars.githubusercontent.com/u/7548925?v=4",
   "gravatar_id":"",
   "url":"https://api.github.com/users/shahrukhamd",
   "html_url":"https://github.com/shahrukhamd",
   "followers_url":"https://api.github.com/users/shahrukhamd/followers",
   "following_url":"https://api.github.com/users/shahrukhamd/following{/other_user}",
   "gists_url":"https://api.github.com/users/shahrukhamd/gists{/gist_id}",
   "starred_url":"https://api.github.com/users/shahrukhamd/starred{/owner}{/repo}",
   "subscriptions_url":"https://api.github.com/users/shahrukhamd/subscriptions",
   "organizations_url":"https://api.github.com/users/shahrukhamd/orgs",
   "repos_url":"https://api.github.com/users/shahrukhamd/repos",
   "events_url":"https://api.github.com/users/shahrukhamd/events{/privacy}",
   "received_events_url":"https://api.github.com/users/shahrukhamd/received_events",
   "type":"User",
   "site_admin":false,
   "name":"ShahRukh",
   "company":"Lalamove",
   "blog":"https://shahrukhamd.com/",
   "location":"Hong Kong",
   "email":null,
   "hireable":true,
   "bio":"Software Engineer @ Lalamove | Android Apps | SDK | Shutterbug | Dreamer | Thinker",
   "twitter_username":"shahrukhamd",
   "public_repos":7,
   "public_gists":2,
   "followers":4,
   "following":0,
   "created_at":"2014-05-11T13:14:05Z",
   "updated_at":"2021-09-12T08:04:09Z"
}""".trimIndent()