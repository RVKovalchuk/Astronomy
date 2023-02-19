package com.example.astronomy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.astronomy.GeneralConstants.KEY
import com.example.astronomy.GeneralFunction.changeVisibility
import com.example.astronomy.GeneralFunction.setDetails
import com.example.astronomy.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {
    private var _binding:
            FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        changeVisibility(binding.detailsFabHide, binding.detailsApodScrollview)
        setDetails(
            arguments?.getParcelable(KEY)!!,
            binding.detailsApodDescriprion,
            binding.detailsApodTitle,
            binding.detailsImage,
            binding.detailsApodDate
        )
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}