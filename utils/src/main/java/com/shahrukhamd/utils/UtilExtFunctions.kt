/*
 * Copyright (c) 2021 Shahrukh Ahmed Siddiqui
 *
 * You may use, distribute and modify this code under the
 * terms of the MIT license - https://opensource.org/licenses/MIT
 */

package com.shahrukhamd.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Register [View.OnClickListener] on ViewHolder root view
 *
 * @param event Callback function receiving root view, item position and type
 * @return returns this view holder
 */
fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(absoluteAdapterPosition, itemViewType)
    }
    return this
}

/**
 * Make a toast from anywhere
 *
 * @param text: Message to show in the toast
 */
fun Context.showToast(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

/**
 * Make a toast from anywhere
 *
 * @param resId: String resource ID of the message to show in the toast
 */
fun Context.showToast(@StringRes resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()