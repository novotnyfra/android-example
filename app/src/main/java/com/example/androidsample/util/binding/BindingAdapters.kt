package com.example.androidsample.util.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.androidsample.R

object BindingAdapters {

    @BindingAdapter("android:visibility")
    @JvmStatic
    fun setVisibility(view: View, visible: Boolean?) {
        if (visible != null)
            view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @BindingAdapter("android:text")
    @JvmStatic
    fun setText(v : TextView, d: Any?){
        v.text = d?.toString()
    }

    @BindingAdapter("android:imageUrl")
    @JvmStatic
    fun loadImage(iv: ImageView, url: String?) {
        Glide
            .with(iv.context)
            .setDefaultRequestOptions(
                RequestOptions()
                    .placeholder(R.drawable.ic_image_placeholder)
                    .error(R.drawable.ic_broken_image))
            .load(url)
            .into(iv)
    }

}
