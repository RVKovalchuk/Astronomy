package com.example.astronomy

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.astronomy.databinding.FragmentAstronomyPictureOfTheDayBinding
import com.example.astronomy.retrofit.AstronomyPictureOfTheDay
import com.example.spaceinfo2.viewModel.AstronomyPictureOfTheDayViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AstronomyPictureOfTheDayFragment : Fragment() {
    private val viewModel: AstronomyPictureOfTheDayViewModel by activityViewModels()
    private var _binding:
            FragmentAstronomyPictureOfTheDayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAstronomyPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeVisibility()
        bindButtonNewFragment()


        Log.d("VVV", "$_binding")


        viewModel.apod.observe(
            viewLifecycleOwner
        ) {
            lifecycleScope.launch(Dispatchers.Main) {
                bindFragment(it)
            }
        }
    }


    private fun bindFragment(apod: AstronomyPictureOfTheDay) {
        Picasso.get()
            .load(apod.url)
            .into(binding.apodImage)
        binding.apodDescriprion.text = apod.explanation
        binding.apodTitle.text = apod.title
    }

    private fun changeVisibility() {
        binding.fabHide.setOnClickListener {
            if (binding.apodScrollview.visibility == View.VISIBLE) {
                binding.apodScrollview.visibility = View.GONE
                binding.fabHide.setImageResource(R.drawable.ic_up)
            } else {
                binding.apodScrollview.visibility = View.VISIBLE
                binding.fabHide.setImageResource(R.drawable.ic_down)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindButtonNewFragment() {
        binding.buttonLeft.setOnClickListener {
            findNavController().navigate(R.id.action_astronomyPictureOfTheDayFragment2_to_astronomyPicturesOfTheDayRecyclerFragment)
        }
    }
}
