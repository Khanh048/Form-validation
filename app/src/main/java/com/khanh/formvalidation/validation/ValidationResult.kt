package com.khanh.formvalidation.validation

import androidx.annotation.StringRes

data class ValidationResult(
    val isValid: Boolean,
    @StringRes val errorMessageResId: Int? = null
)
