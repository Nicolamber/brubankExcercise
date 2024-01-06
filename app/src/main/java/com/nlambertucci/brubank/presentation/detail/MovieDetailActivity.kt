package com.nlambertucci.brubank.presentation.detail

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.nlambertucci.brubank.R
import com.nlambertucci.brubank.common.Constants
import com.nlambertucci.brubank.common.isVisible
import com.nlambertucci.brubank.databinding.ActivityMovieDetailBinding
import com.nlambertucci.brubank.domain.model.DetailDto
import com.nlambertucci.brubank.domain.model.Movie
import com.nlambertucci.brubank.presentation.detail.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private val viewModel: MovieDetailViewModel by viewModels()
    private val binding: ActivityMovieDetailBinding by lazy {
        ActivityMovieDetailBinding.inflate(layoutInflater)
    }

    private var movie: Movie? = null
    private var dominantColor: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        getIntentData()
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {
        viewModel.detailLiveStatus.observe(this) { status ->
            when (status) {
                is MovieDetailViewModel.DetailStatus.Loading -> {
                    binding.errorScreen.hideErrorComponent()
                    binding.loading.showLoadingScreen()
                }
                is MovieDetailViewModel.DetailStatus.Success -> {
                    initUiComponents(status.detailDto)
                    binding.loading.hideLoadingScreen()
                }
                is MovieDetailViewModel.DetailStatus.Error -> {
                    binding.loading.hideLoadingScreen()
                    binding.errorScreen.showErrorComponent {
                        viewModel.initView(movie)
                    }
                }
            }
        }
        viewModel.initView(movie)
    }

    private fun initUiComponents(detailDto: DetailDto) {
        initBackButton()

        Glide.with(this)
            .asBitmap()
            .load(Constants.IMAGE_BASE_PATH + detailDto.movie.posterPath)
            .into(
                object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        binding.moviePoster.setImageBitmap(resource)
                        Palette.from(resource).generate { palette: Palette? ->
                            dominantColor = palette?.dominantSwatch?.rgb ?: Color.TRANSPARENT
                            binding.detailContainer.setBackgroundColor(dominantColor)
                            binding.setAsFavButton.setTextColor(dominantColor)
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // No se va a usar
                    }

                }
            )

        with(binding){
            movieTitle.text = detailDto.movie.title
            movieReleaseDate.text = detailDto.movie.releaseDate
            overviewDescription.text = detailDto.movie.overview

            moviePoster.isVisible = true
            movieTitle.isVisible = true
            movieReleaseDate.isVisible = true
            overviewTitle.isVisible = true
            overviewDescription.isVisible = true
        }

        initFavButton(detailDto)

        binding.detailList.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            val minHeight = 200
            val originalHeight = 400
            val scaleValue = 1.0f.coerceAtMost(1.0f - (scrollY / originalHeight.toFloat()))

            if (scrollY < originalHeight - minHeight) {
                binding.moviePoster.scaleX = scaleValue
                binding.moviePoster.scaleY = scaleValue
            }

        }
    }

    private fun initBackButton() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun initFavButton(detailDto: DetailDto) {
        binding.setAsFavButton.apply {
            text =
                if (detailDto.isFavorite) getString(R.string.is_favorite_text)
                else getString(R.string.is_not_favorite_text)
            setOnClickListener {
                text = if (detailDto.isFavorite) {
                    viewModel.removeMovieFromFavorites(detailDto.movie)
                    getString(R.string.is_not_favorite_text)
                } else {
                    viewModel.setAsFavorite(detailDto.movie)
                    getString(R.string.is_favorite_text)
                }
            }
            isVisible = true
        }
    }

    private fun getIntentData() {
        movie = intent.getParcelableExtra(Constants.MOVIE_DETAIL) as Movie?
    }


    companion object {
        fun newInstance(context: Context, movie: Movie): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(Constants.MOVIE_DETAIL, movie)
            return intent
        }
    }
}