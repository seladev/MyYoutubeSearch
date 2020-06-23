package com.sela.youtubesearch.utils

import android.app.Activity
import android.content.Context
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sela.youtubesearch.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.get

/**
 * Extension
 */

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.setVisible(isVisible:Boolean) {
    if (isVisible) show() else hide()
}

fun Fragment.showToast(message: CharSequence) =
    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()

fun Activity.showToast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


fun Picasso.loadImageOrNull(image:String?, imageView:ImageView){
    if(image.isNullOrBlank()) imageView.setImageResource(R.drawable.ic_error)
    else this
        .load(image)
        .error(R.drawable.ic_error)
        .placeholder(R.drawable.ic_play_video)
        .into(imageView)
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
fun CharSequence?.isValidPassword() = !isNullOrEmpty() && this?.length!! >= 6

