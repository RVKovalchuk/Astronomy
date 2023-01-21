package com.example.astronomy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.astronomy.databinding.FragmentAstronomyPicturesOfTheDayRecyclerBinding
import com.example.astronomy.view.recycler.AstronomyPictureOfTheDayAdapter
import com.example.spaceinfo2.viewModel.AstronomyPictureOfTheDayRecyclerViewModel


class AstronomyPicturesOfTheDayRecyclerFragment : Fragment() {
    private var _binding:
            FragmentAstronomyPicturesOfTheDayRecyclerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AstronomyPictureOfTheDayRecyclerViewModel by activityViewModels()

    private val adapter: AstronomyPictureOfTheDayAdapter by lazy {
        AstronomyPictureOfTheDayAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewApod.adapter = adapter
        viewModel.apodList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }


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