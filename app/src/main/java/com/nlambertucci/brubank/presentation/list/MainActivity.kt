package com.nlambertucci.brubank.presentation.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.nlambertucci.brubank.common.isVisible
import com.nlambertucci.brubank.databinding.ActivityMainBinding
import com.nlambertucci.brubank.domain.model.Movie
import com.nlambertucci.brubank.domain.model.SearchDto
import com.nlambertucci.brubank.presentation.actions.AdapterActionsInterface
import com.nlambertucci.brubank.presentation.detail.MovieDetailActivity
import com.nlambertucci.brubank.presentation.list.adapter.FavoritesAdapter
import com.nlambertucci.brubank.presentation.list.adapter.MovieListAdapter
import com.nlambertucci.brubank.presentation.list.adapter.SearchResultAdapter
import com.nlambertucci.brubank.presentation.list.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MoviesViewModel by viewModels()

    @Inject
    lateinit var movieListAdapter: MovieListAdapter

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
        viewModel.moviesStatus.observe(this) { status ->
            when (status) {
                is MoviesViewModel.MovieStatus.Loading -> {
                    binding.loading.showLoadingScreen()
                }

                is MoviesViewModel.MovieStatus.Success -> {
                    binding.loading.hideLoadingScreen()
                   initUiComponents(status.favorites)
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
                    binding.errorScreen.hideErrorComponent()
                }
            }
        }
        viewModel.initView()

    }

    private fun initUiComponents(favorites: List<Movie>?) {
        binding.recommendedList.layoutManager = LinearLayoutManager(this)
        binding.recommendedList.isVisible = true
        binding.recommendedList.adapter = movieListAdapter
        lifecycleScope.launch {
            viewModel.moviesList.collectLatest {
                movieListAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            movieListAdapter.loadStateFlow.collectLatest{
                val state = it.refresh
                if ( state is LoadState.Loading){
                    binding.apply {
                        recommendedTitle.isVisible = false
                        favoritesTitle.isVisible = false
                        loading.showLoadingScreen()
                    }
                }else {
                    binding.apply {
                        recommendedTitle.isVisible = true
                        favoritesTitle.isVisible = true
                        loading.hideLoadingScreen()
                    }

                }
            }
        }

        favorites ?: return
        binding.favoritesTitle.isVisible = favorites.isNotEmpty()
        binding.favoritesList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.favoritesList.adapter = FavoritesAdapter(this, favorites) {
            navigateToNextScreen(it)
        }
        binding.favoritesList.isVisible = favorites.isNotEmpty()
        binding.favoritesList.isVisible = favorites.isNotEmpty()

        initSearchView()
    }

    private fun showSearchResults(searchResult: SearchDto) {
        binding.searchResultList.layoutManager = LinearLayoutManager(this)
        binding.searchResultList.isNestedScrollingEnabled = false
        binding.searchResultList.isVisible = true
        binding.favoritesList.isVisible = false
        binding.favoritesTitle.isVisible = false
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
        binding.recommendedList.adapter = SearchResultAdapter(this, searchResult, itemActionsListener)
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