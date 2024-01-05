package com.nlambertucci.brubank.presentation.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nlambertucci.brubank.common.Constants
import com.nlambertucci.brubank.common.isVisible
import com.nlambertucci.brubank.databinding.MovieItemBinding
import com.nlambertucci.brubank.domain.model.Movie
import javax.inject.Inject

class MovieListAdapter @Inject constructor() :
    PagingDataAdapter<Movie, MovieListAdapter.MovieViewHolder>(differCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class MovieViewHolder(
        private val binding: MovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.moviePoster.apply {
                Glide.with(context)
                    .load(Constants.IMAGE_BASE_PATH + movie.backdropPath)
                    .into(this)
                isVisible = true
            }
            binding.movieTitle.apply {
                text = movie.title
                isVisible = true
            }
            binding.releaseDate.apply {
                text = movie.releaseDate
                isVisible = true
            }
        }
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}