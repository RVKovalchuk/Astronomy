package com.example.astronomy.view.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.astronomy.databinding.RecyclerItemAstronomyPictureOfTheDayBinding
import com.example.astronomy.retrofit.AstronomyPictureOfTheDay

class AstronomyPictureOfTheDayAdapter :
    ListAdapter<AstronomyPictureOfTheDay, AstronomyPictureOfTheDayViewHolder>(
        AstronomyPictureOfTheDayDiffUtil
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AstronomyPictureOfTheDayViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerItemAstronomyPictureOfTheDayBinding.inflate(inflater, parent, false)
        return AstronomyPictureOfTheDayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AstronomyPictureOfTheDayViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}