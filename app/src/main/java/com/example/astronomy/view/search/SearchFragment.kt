package com.example.astronomy.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.astronomy.AdapterFor
import com.example.astronomy.collectWhenCreated
import com.example.astronomy.data.retrofit.Apod
import com.example.astronomy.data.toObs
import com.example.astronomy.databinding.FragmentSearchBinding
import com.example.astronomy.databinding.ItemSearchRvBinding
import com.example.astronomy.loadImage
import com.example.astronomy.view.favorites.navigateToDetails

class SearchFragment : Fragment() {
    private var binding: FragmentSearchBinding? = null
    private val viewModel: SearchViewModel by activityViewModels()

    private val adapter by lazy {
        AdapterFor(
            lifecycleScope = lifecycleScope,
            equals = Apod::equals,
            inflating = ItemSearchRvBinding::inflate,
            bind = {
                binding.image.loadImage(content.url)
                binding.title.text = content.title
                binding.desc.text = content.explanation

                binding.root.setOnClickListener {
                    navigateToDetails(content.toObs())
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding ?: return

        binding.rv.adapter = adapter
        binding.rv.scrollToPosition(viewModel.rvState)

        collectWhenCreated(adapter.firstVisibleItem, viewModel::rvState::set)

        collectWhenCreated(viewModel.searched, ::setup)
        collectWhenCreated(viewModel.startDate, ::setupStartDate)
        collectWhenCreated(viewModel.endDate, ::setupEndDate)
    }

    private fun setupEndDate(string: String?) {
        val binding = binding ?: return
        binding.endDate.text = string
    }

    private fun setupStartDate(string: String?) {
        val binding = binding ?: return
        binding.startDate.text = string
    }

    private fun setup(list: List<Apod>?) {
        val binding = binding ?: return
        binding.massage.isVisible = list.isNullOrEmpty()
        adapter.submitList(list)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onDestroyView() {
        this.binding = null
        super.onDestroyView()
    }
}