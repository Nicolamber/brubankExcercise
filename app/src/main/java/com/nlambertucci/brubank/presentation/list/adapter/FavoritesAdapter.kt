package com.nlambertucci.brubank.presentation.list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nlambertucci.brubank.common.Constants
import com.nlambertucci.brubank.common.isVisible
import com.nlambertucci.brubank.databinding.FavoriteItemBinding
import com.nlambertucci.brubank.domain.model.Movie

class FavoritesAdapter(
    private val context: Context,
    private val movies: List<Movie>,
    private val onItemSelected: (Movie) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding = FavoriteItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoritesViewHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(movies[position], context)
        holder.itemView.setOnClickListener { onItemSelected(movies[position]) }
    }


    inner class FavoritesViewHolder(
        private val binding: FavoriteItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, context: Context) {
            Glide.with(context)
                .load(Constants.IMAGE_BASE_PATH + movie.posterPath)
                .into(binding.favPoster)
            binding.favPoster.isVisible = true
        }
    }
}