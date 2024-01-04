package com.nlambertucci.brubank.presentation.customcomponents

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.nlambertucci.brubank.common.isVisible
import com.nlambertucci.brubank.databinding.LoadingScreenComponentBinding

class LoadingScreen @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: LoadingScreenComponentBinding by lazy {
        LoadingScreenComponentBinding.inflate(LayoutInflater.from(context),this, true)
    }

    fun showLoadingScreen() {
        binding.loadingContainer.isVisible = true
    }

    fun hideLoadingScreen() {
        binding.loadingContainer.isVisible = false
    }
}