package com.example.astronomy

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadImage(url: String?) {
    if (url?.isEmpty() == false) {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.image_place_holder)
            .fit()
            .centerCrop()
            .into(this)
    } else {
        Picasso.get()
            .load(R.drawable.image_place_holder)
            .fit()
            .centerCrop()
            .into(this)
    }
}