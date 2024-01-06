package com.nlambertucci.brubank.presentation.list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nlambertucci.brubank.common.Constants
import com.nlambertucci.brubank.common.isVisible
import com.nlambertucci.brubank.databinding.MovieItemBinding
import com.nlambertucci.brubank.domain.model.Movie

class MovieListAdapter(
    private val context: Context,
    private val movies: List<Movie>,
    private val onItemSelected: (Movie) -> Unit
) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
        holder.itemView.setOnClickListener { onItemSelected(movies[position]) }
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
}