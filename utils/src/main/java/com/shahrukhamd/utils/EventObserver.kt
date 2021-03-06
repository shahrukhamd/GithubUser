/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.utils

import androidx.lifecycle.Observer

/**
 * A helper class which will trigger the listener for live data change only if it's not consumed
 *
 * @param onEventNotConsumedObserver: function that will be triggered with event data
 */
class EventObserver<T>(private val onEventNotConsumedObserver: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getDataIfNotConsumed()?.let {
            onEventNotConsumedObserver(it)
        }
    }
}