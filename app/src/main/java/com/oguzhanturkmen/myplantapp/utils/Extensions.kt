package com.oguzhanturkmen.myplantapp.utils

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.oguzhanturkmen.myplantapp.R

fun Navigation.gecisYap(view: View, id: Int) {
    findNavController(view).navigate(id)

}

fun Navigation.gecisYap(view: View, id: NavDirections) {
    findNavController(view).navigate(id)

}

fun makeToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}


fun EditText.trimmedText(): String {
    return this.text.toString().trim()
}

fun ImageView.downloadImageProfile(url: String?) {
    if (url == "none"){
        this.setImageResource(R.drawable.user_image)
    }else Glide.with(context).load(url).into(this)
}

@BindingAdapter("app:downloadImage")
fun downloadImageFromUrl(view: ImageView, url: String?) {
    view.downloadImageProfile(url)
}

fun View.showErrorSnackBar(message: String, errorMessage: Boolean) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    val snackBarView = snackBar.view

    if (errorMessage) {
        snackBarView.setBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.color_snack_bar_error
            )
        )
    } else {
        snackBarView.setBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.color_snack_bar_success
            )
        )
    }
    snackBar.show()
}
