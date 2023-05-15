package com.example.astronomy.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.navigation.fragment.findNavController
import com.example.astronomy.R
import com.example.astronomy.databinding.FragmentDetailsBinding
import com.example.astronomy.getColorList
import com.example.astronomy.loadImage
import com.example.astronomy.shareImage
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {

    private var binding: FragmentDetailsBinding? = null
    private val viewModel: DetailsViewModel by activityViewModels()

    private var subscribeOnOpenedItem: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding ?: return

        binding.cancelBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        lifecycleScope.launch {
            lifecycle.whenCreated {
                viewModel.openedItem.collect { apod ->
                    if (apod != null) {
                        subscribeOnOpenedItem?.cancel()
                        binding.image.loadImage(apod.apod.hdurl)
                        binding.title.text = apod.apod.title
                        binding.desc.text = apod.apod.explanation

                        binding.favoriteBtn.setOnClickListener {
                            viewModel.changeLike(apod)
                        }

                        binding.share.setOnClickListener {
                            requireContext().shareImage(apod.apod)
                        }

                        binding.download.setOnClickListener {
                            requireContext().loadImage(apod.apod)
                        }

                        subscribeOnOpenedItem = lifecycleScope.launch {
                            lifecycle.whenCreated {
                                apod.favoriteState.collect {
                                    binding.favoriteBtn.setCardBackgroundColor(
                                        requireContext().getColorList(
                                            if (it) R.color.blue else R.color.black
                                        ),
                                    )
                                    binding.favoriteBtnText.text = getString(
                                        if (it) R.string.in_favorite_btn else R.string.favorite_btn
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailsBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}