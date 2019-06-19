package com.example.androidsample.util.binding

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

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

}
