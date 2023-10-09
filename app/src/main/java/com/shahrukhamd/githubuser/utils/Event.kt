/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.utils

/**
 * A wrapper for data that is exposed via a LiveData and represents an event.
 *
 * @property data the wrapped data
 */
class Event<T>(private val data: T?) {

    /**
     * Flag to indicated the data is consumed.
     */
    var isDataConsumed: Boolean = false
        private set

    fun getDataIfNotConsumed(): T? {
        return if (isDataConsumed) {
            null
        } else {
            isDataConsumed = true
            data
        }
    }

    /**
     * Get the data without marking it as consumed
     */
    fun peek(): T? = data
}