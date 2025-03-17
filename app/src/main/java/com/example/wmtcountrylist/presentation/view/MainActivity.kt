package com.example.wmtcountrylist.presentation.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wmtcountrylist.api.ApiClient
import com.example.wmtcountrylist.data.CountryRepository
import com.example.wmtcountrylist.databinding.ActivityMainBinding
import com.example.wmtcountrylist.domain.GetCountriesUseCaseImpl
import com.example.wmtcountrylist.presentation.adapter.CountryAdapter
import com.example.wmtcountrylist.presentation.viewmodel.CountryViewModel
import com.example.wmtcountrylist.presentation.viewmodel.CountryViewModelFactory
import com.example.wmtcountrylist.utils.ResultWrapper
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var countryAdapter: CountryAdapter
    private lateinit var viewModel: CountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = ApiClient.apiService
        val repository = CountryRepository(apiService)
        val useCase = GetCountriesUseCaseImpl(repository)
        viewModel = ViewModelProvider(this, CountryViewModelFactory(useCase))[CountryViewModel::class.java]

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
        lifecycleScope.launch {
            viewModel.countries.collectLatest { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                        binding.errorTextView.visibility = View.GONE
                    }
                    is ResultWrapper.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.errorTextView.visibility = View.GONE
                        countryAdapter.submitList(result.data)
                    }
                    is ResultWrapper.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerView.visibility = View.GONE
                        binding.errorTextView.visibility = View.VISIBLE
                        binding.errorTextView.text = result.message
                    }
                }
            }
        }
    }
}
