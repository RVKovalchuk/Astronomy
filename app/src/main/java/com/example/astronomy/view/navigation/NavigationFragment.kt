package com.example.astronomy.view.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.navigation.findNavController
import com.example.astronomy.R
import com.example.astronomy.backOrNavigate
import com.example.astronomy.collectWhenStarted
import com.example.astronomy.databinding.FragmentNavigationBinding
import com.example.astronomy.getColorList
import kotlinx.coroutines.launch

class NavigationFragment : Fragment() {

    private var binding: FragmentNavigationBinding? = null
    private val viewModel: NavigationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavBarBtn()

        collectWhenStarted(viewModel.navState) {
            setNavBarState(it)
            setFragment(it)
        }

        lifecycleScope.launch {
            lifecycle.whenStarted {
                val binding = binding ?: return@whenStarted
                val navController = binding.navHostFragment.findNavController()
                collectWhenStarted(navController.visibleEntries) {
                    val destination = it.firstOrNull()?.destination?.id ?: return@collectWhenStarted
                    viewModel.navigate(destination)
                }
            }
        }
    }

    private fun setFragment(navState: NavState) {
        val binding = this.binding ?: return

        binding.navHostFragment.findNavController().backOrNavigate(
            when (navState) {
                NavState.Favorites -> R.id.favorites
                NavState.Search -> R.id.search
                NavState.Settings -> R.id.settings
                else -> R.id.home
            }
        )
    }

    private fun setupNavBarBtn() {
        val binding = binding ?: return

        binding.home.setOnClickListener { viewModel.navigate(NavState.Home) }
        binding.search.setOnClickListener { viewModel.navigate(NavState.Search) }
        binding.favorites.setOnClickListener { viewModel.navigate(NavState.Favorites) }
    }

    private fun setNavBarState(navState: NavState) {
        val binding = binding ?: return

        val selectedColor = requireContext().getColorList(R.color.blue)
        val unselectedColor = requireContext().getColorList(R.color.gray_3)

        val homeColor = if (navState == NavState.Home) selectedColor else unselectedColor
        val searchColor = if (navState == NavState.Search) selectedColor else unselectedColor
        val favoritesColor = if (navState == NavState.Favorites) selectedColor else unselectedColor
        val settingsColor = if (navState == NavState.Settings) selectedColor else unselectedColor

        binding.homeText.setTextColor(homeColor)
        binding.homeImage.imageTintList = homeColor

        binding.searchText.setTextColor(searchColor)
        binding.searchImage.imageTintList = searchColor

        binding.favoritessText.setTextColor(favoritesColor)
        binding.favoritesImage.imageTintList = favoritesColor

        binding.settingsText.setTextColor(settingsColor)
        binding.settingsImage.imageTintList = settingsColor
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNavigationBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}