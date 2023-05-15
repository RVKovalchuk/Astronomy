package com.example.astronomy.view.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.findNavController
import com.example.astronomy.R
import com.example.astronomy.databinding.FragmentLoadingBinding
import kotlinx.coroutines.launch

class LoadingFragment : Fragment() {

    private var binding: FragmentLoadingBinding? = null
    private val viewModel: LoadingFragmentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = binding ?: return

        lifecycleScope.launch {
            lifecycle.whenStarted {
                viewModel.progress.collect {
                    binding.progressBar.progress = it
                    if (it == 100) findNavController().navigate(R.id.action_loading_to_navigation)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.startProcess()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLoadingBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}