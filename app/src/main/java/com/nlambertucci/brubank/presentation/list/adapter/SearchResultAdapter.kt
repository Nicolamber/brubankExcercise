package com.nlambertucci.brubank.presentation.list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nlambertucci.brubank.R
import com.nlambertucci.brubank.common.Constants
import com.nlambertucci.brubank.common.isVisible
import com.nlambertucci.brubank.databinding.SearchResultItemBinding
import com.nlambertucci.brubank.domain.model.Movie
import com.nlambertucci.brubank.domain.model.SearchDto
import com.nlambertucci.brubank.presentation.actions.AdapterActionsInterface

class SearchResultAdapter(
    private val context: Context,
    private val searchResultMovies: SearchDto,
    private val adapterActions: AdapterActionsInterface
): RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SearchResultItemBinding.inflate(inflater, parent, false)
        return SearchResultViewHolder(binding)
    }

    override fun getItemCount(): Int = searchResultMovies.movies.size

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(searchResultMovies.movies[position], searchResultMovies.favoritesMovies)
    }

    inner class SearchResultViewHolder(
        private val binding: SearchResultItemBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(movie: Movie, favorites: List<Movie>?){

            Glide.with(context)
                .load(Constants.IMAGE_BASE_PATH + movie.backdropPath)
                .into(binding.searchResultImage)
            binding.searchResultImage.isVisible = true

            binding.searchResultTitle.apply {
                text = movie.title
                isVisible = true
            }

            binding.searchResultButton.apply {
                if (favorites?.contains(movie) == true){
                    text = context.getString(R.string.delete_from_fav_text)
                    setOnClickListener {
                        text = context.getString(R.string.add_to_fav_text)
                        adapterActions.onRemoveMovieClicked(movie)
                    }
                }else {
                    text = context.getString(R.string.add_to_fav_text)
                    setOnClickListener {
                        text = context.getString(R.string.delete_from_fav_text)
                        adapterActions.onAddMovieClicked(movie) }
                }
            }

            binding.searchResultContainer.setOnClickListener {
                adapterActions.onContainerClicked(movie)
            }
        }
    }
}

