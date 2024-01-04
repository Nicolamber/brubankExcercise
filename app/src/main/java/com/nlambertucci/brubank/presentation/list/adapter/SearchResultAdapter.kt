package com.nlambertucci.brubank.presentation.list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nlambertucci.brubank.databinding.SearchResultItemBinding
import com.nlambertucci.brubank.domain.model.Movie
import com.nlambertucci.brubank.presentation.actions.AdapterActionsInterface

class SearchResultAdapter(
    private val context: Context,
    private val searchResultMovies: List<Movie>,
    private val adapterActions: AdapterActionsInterface
): RecyclerView.Adapter<SearchResultViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SearchResultItemBinding.inflate(inflater, parent, false)
        return SearchResultViewHolder(binding)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}

class SearchResultViewHolder(
    private val binding: SearchResultItemBinding
): RecyclerView.ViewHolder(binding.root){

}