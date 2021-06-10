/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.utils

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.Success, data, null)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.Loading, null, null)
        }

        fun <T> error(data: T, msg: String?): Resource<T> {
            return Resource(Status.Error, data, msg)
        }
    }
}
