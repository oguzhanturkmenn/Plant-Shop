package com.oguzhanturkmen.myplantapp.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.oguzhanturkmen.myplantapp.R


@BindingAdapter("android:downloadUrl")
fun ImageView.downloadImage(url: String?) {
    Glide.with(context).load(url).into(this)
}

