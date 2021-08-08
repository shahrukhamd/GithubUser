/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.githubuser.utils

import androidx.recyclerview.widget.RecyclerView

/**
 * Register [View.OnClickListener] on ViewHolder root view
 *
 * @see <a href="https://github.com/Schibsted-Tech-Polska/kotlin-kit/blob/master/core/src/main/kotlin/pl/schibsted/kotlinkit/core/extensions/ViewHolder.kt">
 *
 * @param event callback function receiving root view, item position and type
 * @return returns this view holder
 */
fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(absoluteAdapterPosition, itemViewType)
    }
    return this
}