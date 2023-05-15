package com.example.astronomy.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.astronomy.R
import com.example.astronomy.collectWhenCreated
import com.example.astronomy.data.ApodFromView
import com.example.astronomy.databinding.FragmentDetailsBinding
import com.example.astronomy.loadImage
import com.example.astronomy.shareImage

class HomeFragment : Fragment() {

    private var binding: FragmentDetailsBinding? = null
    private val viewModel: HomeFragmentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectWhenCreated(viewModel.apod, ::setup)
    }

    private fun setup(_apod: ApodFromView?) {
        val binding = binding ?: return
        val apod = _apod ?: return

        binding.cancelBtn.isVisible = false
        binding.title.text = apod.apod.title
        binding.date.text = apod.apod.date
        binding.desc.text = apod.apod.explanation
        binding.image.loadImage(apod.apod.hdurl)

        binding.share.setOnClickListener {
            requireContext().shareImage(apod.apod)
        }

        binding.download.setOnClickListener {
            requireContext().loadImage(apod.apod)
        }

        binding.favoriteBtn.setOnClickListener {
            viewModel.changeFavoriteState(apod)
        }

        collectWhenCreated(apod.favoriteState, ::setupFavorite)
    }

    private fun setupFavorite(isFavorite: Boolean) {
        val binding = binding ?: return
        val btnTextId = if (isFavorite) R.string.favorite_btn else R.string.in_favorite_btn
        val btnColorId = if (isFavorite) R.color.blue else R.color.black_2

        binding.favoriteBtnText.text = getString(btnTextId)
        binding.favoriteBtn.setCardBackgroundColor(requireContext().getColorStateList(btnColorId))
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
        super.onDestroyView()
        this.binding = null
    }
}