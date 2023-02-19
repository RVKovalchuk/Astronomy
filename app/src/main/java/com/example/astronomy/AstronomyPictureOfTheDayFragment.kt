package com.example.astronomy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.astronomy.GeneralFunction.changeVisibility
import com.example.astronomy.GeneralFunction.setDetails
import com.example.astronomy.databinding.FragmentAstronomyPictureOfTheDayBinding
import com.example.astronomy.viewModel.AstronomyPictureOfTheDayViewModel
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
        changeVisibility(binding.fabHide, binding.apodScrollview)

        viewModel.apod.observe(
            viewLifecycleOwner
        ) {
            lifecycleScope.launch(Dispatchers.Main) {
                if (it != null) {
                    setDetails(
                        it,
                        binding.apodDescriprion,
                        binding.apodTitle,
                        binding.apodImage,
                        binding.apodDate
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
