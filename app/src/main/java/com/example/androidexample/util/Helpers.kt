package com.example.androidexample.util

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun hideKeyboard(view: View) {
    val imm = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun showKeyboard(view: View) {
    val imm = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}
