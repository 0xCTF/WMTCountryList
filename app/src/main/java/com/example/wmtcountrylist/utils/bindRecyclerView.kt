package com.example.wmtcountrylist.utils

import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.doOnAttach
import androidx.core.view.doOnDetach
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.wmtcountrylist.data.Country
import com.example.wmtcountrylist.presentation.adapter.CountryAdapter
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@BindingAdapter("countriesList", "progressBar", "errorTextView", requireAll = true)
fun bindRecyclerView(
    recyclerView: RecyclerView,
    countriesFlow: StateFlow<ResultWrapper<List<Country>>>?,
    progressBar: ProgressBar?,
    errorTextView: TextView?
) {
    recyclerView.doOnAttach {
        val lifecycleOwner = recyclerView.findViewTreeLifecycleOwner() ?: return@doOnAttach
        val adapter = (recyclerView.adapter as? CountryAdapter) ?: return@doOnAttach

        val job = lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                countriesFlow?.collect { result ->
                    when (result) {
                        is ResultWrapper.Loading -> {
                            progressBar?.isVisible = true
                            recyclerView.isVisible = false
                            errorTextView?.isVisible = false
                        }

                        is ResultWrapper.Success -> {
                            progressBar?.isVisible = false
                            recyclerView.isVisible = true
                            errorTextView?.isVisible = false
                            adapter.submitList(result.data)
                        }

                        is ResultWrapper.Error -> {
                            progressBar?.isVisible = false
                            recyclerView.isVisible = false
                            errorTextView?.apply {
                                isVisible = true
                                text = result.message
                            }
                        }
                    }
                }
            }
        }

        recyclerView.doOnDetach {
            job.cancel()
        }
    }
}


