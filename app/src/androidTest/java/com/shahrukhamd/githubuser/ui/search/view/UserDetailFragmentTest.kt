/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.ui.search.view

import android.content.Intent
import android.net.Uri
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.shahrukhamd.githubuser.R
import com.shahrukhamd.githubuser.SampleUserDetailsObj
import com.shahrukhamd.githubuser.data.repository.SearchRepository
import com.shahrukhamd.githubuser.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class UserDetailFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repo: SearchRepository

    @Before
    fun setup() {
        hiltRule.inject()
        Intents.init()
        val bundle = UserDetailFragmentArgs(SampleUserDetailsObj).toBundle()
        launchFragmentInHiltContainer<UserDetailFragment>(bundle, R.style.AppTheme)
    }

    @After
    fun teardown() {
        Intents.release()
    }

    @Test
    fun whenLaunchUserDetailsFragmentThenLayoutIsDisplayed() {
        onView(withId(R.id.vg_user_details))
            .check(matches(isDisplayed()))
    }

    @Test
    fun whenLaunchUserDetailsFragmentThenOpenButtonIsDisplayedAndClickable() {
        onView(withId(R.id.btn_user_detail_profile_open))
            .check(matches(isDisplayed()))
            .check(matches(isClickable()))
            .check(matches(withText(R.string.open_profile)))
    }

    @Test
    fun whenLaunchUserDetailsFragmentThenShareButtonIsDisplayedAndClickable() {
        onView(withId(R.id.btn_user_detail_profile_share))
            .check(matches(isDisplayed()))
            .check(matches(isClickable()))
            .check(matches(withText(R.string.share_profile)))
    }

    @Test
    fun whenLaunchUserDetailsFragmentThenUserNameShouldBeSameAsSampleUserName() {
        onView(withId(R.id.tv_user_detail_name))
            .check(matches(isDisplayed()))
            .check(matches(withText(SampleUserDetailsObj.name)))
    }

    @Test
    fun whenLaunchUserDetailsFragmentThenUserBioShouldBeSameAsSampleUserBio() {
        onView(withId(R.id.tv_user_detail_bio))
            .check(matches(isDisplayed()))
            .check(matches(withText(SampleUserDetailsObj.bio)))
    }

    @Test
    fun whenLaunchUserDetailsFragmentThenUserLocationShouldBeSameAsSampleUserLocation() {
        onView(withId(R.id.tv_user_detail_location))
            .check(matches(isDisplayed()))
            .check(matches(withText(SampleUserDetailsObj.location)))
    }

    @Test
    fun whenClickShareButtonThenShareSheetShouldOpen() {
        onView(withId(R.id.btn_user_detail_profile_share))
            .perform(click())

        intended(
            allOf(
                hasAction(Intent.ACTION_CHOOSER)
            )
        )
    }

    @Test
    fun whenClickOpenButtonThenViewIntentShouldLaunch() {
        onView(withId(R.id.btn_user_detail_profile_open))
            .perform(click())

        intended(
            allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(Uri.parse(SampleUserDetailsObj.htmlUrl))
            )
        )
    }

    @Test
    fun whenUserIsNotStarredThenStarViewClickShouldMakeItStarred() {
        runBlockingTest {
            // update user as not starred in repo
            var user = repo.getUserDetailsAndUpdateDb("")!!
            user.isUserStared = false
            repo.updateUser(user)

            onView(withId(R.id.imv_user_detail_star))
                .perform(click())

            // update user from repo
            user = repo.getUserDetailsAndUpdateDb("")!!

            // then check if user is starred
            assertThat(user.isUserStared).isTrue()
        }
    }

    @Test
    fun whenUserIsStarredThenStarViewClickShouldMakeItNotStarred() {
        runBlockingTest {
            // update user as starred in repo
            var user = repo.getUserDetailsAndUpdateDb("")!!
            user.isUserStared = true
            repo.updateUser(user)

            onView(withId(R.id.imv_user_detail_star))
                .perform(click())

            // update user from repo
            user = repo.getUserDetailsAndUpdateDb("")!!

            // then check if user is not starred
            assertThat(user.isUserStared).isFalse()
        }
    }
}