package com.example.astronomy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.astronomy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    /*
        private val viewModel: LoadingFragmentViewModel by viewModels()
        private val repository by lazy {
            (application as App).repository
        }
    */
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
/*
        lifecycleScope.launch {
            lifecycle.whenStarted {
                repository.loadingState.collect {
                    if (it is LoadState.Error) {
                        showErrorTask({ this@MainActivity }) {
                            val navController = binding.root.findNavController()
                            viewModel.stopProcess()
                            navController.backOrNavigate(R.id.loadingFragment)
                        }
                    }
                }
            }
        }*/
    }
}
