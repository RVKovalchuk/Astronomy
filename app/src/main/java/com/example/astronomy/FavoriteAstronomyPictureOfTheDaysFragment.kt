package com.example.astronomy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.astronomy.databinding.FragmentAstronomyPicturesOfTheDayRecyclerBinding


class FavoriteAstronomyPictureOfTheDaysFragment : Fragment() {
    private var _binding:
            FragmentAstronomyPicturesOfTheDayRecyclerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAstronomyPicturesOfTheDayRecyclerBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}