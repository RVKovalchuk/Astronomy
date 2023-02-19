package com.example.astronomy.view.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.astronomy.databinding.RecyclerItemAstronomyPictureOfTheDayBinding
import com.example.astronomy.retrofit.AstronomyPictureOfTheDay
import javax.security.auth.callback.Callback

class AstronomyPictureOfTheDayAdapter(
    val onItemClick: (AstronomyPictureOfTheDay) -> Unit = {}
) : ListAdapter<AstronomyPictureOfTheDay, AstronomyPictureOfTheDayViewHolder>(
        AstronomyPictureOfTheDayDiffUtil
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AstronomyPictureOfTheDayViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerItemAstronomyPictureOfTheDayBinding.inflate(inflater, parent, false)
        val holder = AstronomyPictureOfTheDayViewHolder(binding)
        holder.itemView.setOnClickListener {
            onItemClick(getItem(holder.absoluteAdapterPosition))
        }
        return holder
    }

    override fun onBindViewHolder(holder: AstronomyPictureOfTheDayViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}