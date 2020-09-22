package com.ericampire.android.demo.androiddemo

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ericampire.android.demo.androiddemo.databinding.ItemCountryBinding
import com.ericampire.android.demo.androiddemo.model.Country

class CountryAdapter : PagingDataAdapter<Country, CountryViewHolder>(Companion) {
    companion object : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val binding = holder.binding as ItemCountryBinding
        binding.country = getItem(position) ?: return
        binding.executePendingBindings()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCountryBinding.inflate(layoutInflater, parent, false)
        return CountryViewHolder(binding)
    }
}

class CountryViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)