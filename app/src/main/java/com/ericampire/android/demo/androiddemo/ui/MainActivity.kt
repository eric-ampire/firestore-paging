package com.ericampire.android.demo.androiddemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.ericampire.android.demo.androiddemo.CountryAdapter
import com.ericampire.android.demo.androiddemo.R
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private val countryAdapter = CountryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<RecyclerView>(R.id.rvListCountry).adapter = countryAdapter

        lifecycleScope.launch {
            mainViewModel.flow.collect {
                countryAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            countryAdapter.loadStateFlow.collectLatest { loadStates ->
                findViewById<ProgressBar>(R.id.progressBar).isVisible = loadStates.refresh is LoadState.Loading
                findViewById<ProgressBar>(R.id.progressBarLoadMore).isVisible = loadStates.append is LoadState.Loading
            }
        }
    }
}