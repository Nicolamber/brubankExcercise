package com.nlambertucci.brubank.presentation.customcomponents

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.nlambertucci.brubank.common.isVisible
import com.nlambertucci.brubank.databinding.ErrorScreenComponentBinding

class ErrorScreen @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ErrorScreenComponentBinding by lazy {
        ErrorScreenComponentBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun showErrorComponent(listener: () -> Unit){
        binding.errorContainer.isVisible = true
        binding.retryAction.setOnClickListener {
            listener.invoke()
        }
    }

    fun hideErrorComponent(){
        binding.errorContainer.isVisible = false
    }
}