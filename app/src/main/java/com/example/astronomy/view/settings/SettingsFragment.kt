package com.example.astronomy.view.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.example.astronomy.R
import com.example.astronomy.collectWhenStarted
import com.example.astronomy.databinding.FragmnetSettingsBinding
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {

    private var binding: FragmnetSettingsBinding? = null
    private val viewModel: SettingsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding ?: return

        binding.clearBtn.setOnClickListener {
            viewModel.clearCash {
                lifecycleScope.launch {
                    lifecycle.whenStarted {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.cash_cleared),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.nRSwitch.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
            viewModel.changeRepoNigthState(isChecked)
        }

        collectWhenStarted(viewModel.nightMode, ::onMode)
    }

    private fun onMode(isNight: Boolean?) {
        val binding = binding ?: return

        binding.nRSwitch.isChecked = isNight ?: false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmnetSettingsBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}