package com.example.astronomy.view.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.findNavController
import com.example.astronomy.AdapterFor
import com.example.astronomy.R
import com.example.astronomy.collectWhenCreated
import com.example.astronomy.data.ApodFromView
import com.example.astronomy.databinding.FragmentFavoriteBinding
import com.example.astronomy.databinding.ItemFavoriteRvBinding
import com.example.astronomy.loadImage
import com.example.astronomy.view.details.DetailsViewModel
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {

    private var binding: FragmentFavoriteBinding? = null
    private val viewModel: FavoritesViewModel by activityViewModels()

    private val adapter by lazy {
        AdapterFor(
            lifecycleScope = lifecycleScope,
            equals = ApodFromView::equals,
            inflating = ItemFavoriteRvBinding::inflate,
            bind = {
                binding.img.loadImage(content.apod.url)
                binding.title.text = content.apod.title
                binding.desc.text = content.apod.explanation

                binding.root.setOnClickListener {
                    navigateToDetails(content)
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding ?: return

        binding.recycler.adapter = adapter
        binding.message.text = getString(R.string.favorites_empty)

        binding.recycler.scrollToPosition(viewModel.recyclerScrollState)

        collectWhenCreated(adapter.firstVisibleItem, viewModel::recyclerScrollState::set)

        collectWhenCreated(viewModel.favorites, ::setup)
    }

    private fun setup(list: List<ApodFromView>?) {
        val binding = binding ?: return
        binding.message.isVisible = list.isNullOrEmpty()
        adapter.submitList(list)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onDestroyView() {
        this.binding = null
        super.onDestroyView()
    }
}

fun Fragment.navigateToDetails(apod: ApodFromView) {
    lifecycleScope.launch {
        lifecycle.whenStarted {
            val detailsViewModel: DetailsViewModel by activityViewModels()
            detailsViewModel.setItem(apod)

            requireParentFragment().requireParentFragment().findNavController()
                .navigate(R.id.details)
        }
    }
}