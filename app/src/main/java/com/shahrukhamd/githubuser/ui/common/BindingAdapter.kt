package com.shahrukhamd.githubuser.ui.common

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/**
 * @param isVisible boolean value to change visibility of a view
 */
@BindingAdapter(value = ["setVisibility"])
fun View.setVisibility(isVisible: Boolean?) {
    visibility = if (isVisible != null && isVisible) View.VISIBLE else View.GONE
}

/**
 * @param url url of the source to load the image from
 * @param placeholder a default image to be used as placeholder before source image is loaded or in error
 * @param circleCropEnabled crop the source image as circular in shape
 */
@BindingAdapter(value = ["loadImage", "placeholder", "circleCropEnabled"], requireAll = false)
fun ImageView.loadImage(url: String?, placeholder: Drawable?, circleCropEnabled: Boolean?) {
    Glide.with(context).load(url).also { glide ->
        placeholder?.let {
            glide.placeholder(it)
            glide.error(it)
        }
        if (circleCropEnabled == true) {
            glide.circleCrop()
        }
    }.into(this)
}

/**
 * @param dividerItemDecorationOrientation 0 for LinearLayout.HORIZONTAL and 1 for LinearLayout.VERTICAL
 */
@BindingAdapter(value = ["dividerItemDecorationOrientation"])
fun RecyclerView.setDividerItemDecoration(dividerItemDecorationOrientation: Int) {
    addItemDecoration(DividerItemDecoration(context, dividerItemDecorationOrientation))
}
