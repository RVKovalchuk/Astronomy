package com.example.astronomy

import android.view.View
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import com.example.astronomy.retrofit.AstronomyPictureOfTheDay
import com.squareup.picasso.Picasso

object GeneralFunction {
    fun changeVisibility(fabHide: AppCompatImageButton, scroll: ScrollView) {
        fabHide.setOnClickListener {
            if (scroll.visibility == View.GONE) {
                scroll.visibility = View.VISIBLE
                fabHide.setImageResource(R.drawable.ic_down)
            } else {
                scroll.visibility = View.GONE
                fabHide.setImageResource(R.drawable.ic_up)
            }
        }
    }

    fun setDetails(
        apod: AstronomyPictureOfTheDay,
        description: TextView,
        title: TextView,
        image: ImageView, date: TextView
    ) {
        description.text = apod.explanation
        title.text = apod.title
        date.text = apod.date

        Picasso.get()
            .load(apod.url)
            .into(image)
    }
}