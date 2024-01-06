package com.nlambertucci.brubank.presentation.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.nlambertucci.brubank.common.isVisible
import com.nlambertucci.brubank.databinding.ActivityMainBinding
import com.nlambertucci.brubank.domain.model.Movie
import com.nlambertucci.brubank.domain.model.MovieListDto
import com.nlambertucci.brubank.domain.model.SearchDto
import com.nlambertucci.brubank.presentation.actions.AdapterActionsInterface
import com.nlambertucci.brubank.presentation.detail.MovieDetailActivity
import com.nlambertucci.brubank.presentation.list.adapter.FavoritesAdapter
import com.nlambertucci.brubank.presentation.list.adapter.MovieListAdapter
import com.nlambertucci.brubank.presentation.list.adapter.SearchResultAdapter
import com.nlambertucci.brubank.presentation.list.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MoviesViewModel by viewModels()

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {
        viewModel.moviesLiveStatus.observe(this) { status ->
            when (status) {
                is MoviesViewModel.MovieStatus.Loading -> {
                    binding.errorScreen.hideErrorComponent()
                    binding.loading.showLoadingScreen()
                }

                is MoviesViewModel.MovieStatus.Success -> {
                    initUiComponents(status.moviesDto)
                    binding.loading.hideLoadingScreen()
                }

                is MoviesViewModel.MovieStatus.SearchSuccess -> {
                    binding.loading.hideLoadingScreen()
                    showSearchResults(status.searchResult)
                }
                is MoviesViewModel.MovieStatus.Error -> {
                    binding.loading.hideLoadingScreen()
                    binding.errorScreen.showErrorComponent {
                        viewModel.initView()
                    }
                }
            }
        }
        viewModel.initView()
    }

    private fun initUiComponents(moviesDto: MovieListDto) {
        with(binding){
            recommendedTitle.isVisible = true
            recommendedList.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                isNestedScrollingEnabled = false
                adapter = MovieListAdapter(this@MainActivity, moviesDto.movies) {
                    navigateToNextScreen(it)
                }
                isVisible = true
            }
        }


        moviesDto.favorites ?: return
        with(binding){
            favoritesTitle.isVisible = moviesDto.favorites.isNotEmpty()
            favoritesList.apply {
                layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                adapter = FavoritesAdapter(this@MainActivity, moviesDto.favorites) {
                    navigateToNextScreen(it)
                }
                isVisible = moviesDto.favorites.isNotEmpty()
            }
        }

        initSearchView()
    }


    private fun showSearchResults(searchResult: SearchDto) {
        with(binding){
            searchResultList.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                isNestedScrollingEnabled = false
               isVisible = true
            }
            favoritesList.isVisible = false
            favoritesTitle.isVisible = false
            recommendedTitle.isVisible = false
            recommendedList.isVisible = false
        }
        val itemActionsListener = object : AdapterActionsInterface {
            override fun onContainerClicked(movie: Movie) {
                navigateToNextScreen(movie)
            }

            override fun onAddMovieClicked(movie: Movie) {
                viewModel.setAsFavorite(movie)
            }

            override fun onRemoveMovieClicked(movie: Movie) {
                viewModel.removeMovieFromFavorites(movie)
            }

        }

        binding.searchResultList.adapter =
            SearchResultAdapter(this, searchResult, itemActionsListener)
    }

    private fun initSearchView() {
        binding.searchMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchMovie(query) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        binding.searchMovie.setOnCloseListener {
            binding.searchResultList.isVisible = false
            binding.favoritesList.isVisible = true
            binding.favoritesTitle.isVisible = true
            binding.recommendedList.isVisible = true
            binding.recommendedTitle.isVisible = true
            viewModel.initView()
            false
        }
    }

    private fun navigateToNextScreen(movie: Movie) {
        val nextScreen = MovieDetailActivity.newInstance(this, movie)
        startActivity(nextScreen)
    }
}