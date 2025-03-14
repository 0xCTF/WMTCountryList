package com.example.wmtcountrylist.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wmtcountrylist.databinding.ActivityMainBinding
import com.example.wmtcountrylist.presentation.adapter.CountryAdapter
import com.example.wmtcountrylist.presentation.viewmodel.CountryViewModel
import com.example.wmtcountrylist.utils.ResultWrapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: CountryViewModel by viewModels()
    private lateinit var countryAdapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupRecyclerView()

        observeCountries()
    }

    private fun setupRecyclerView() {
        countryAdapter = CountryAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = countryAdapter
        }
    }

    private fun observeCountries() {
        lifecycleScope.launchWhenStarted {
            viewModel.countries.collectLatest { result ->
                if (result is ResultWrapper.Success) {
                    countryAdapter.submitList(result.data)
                }
            }
        }
    }
}
