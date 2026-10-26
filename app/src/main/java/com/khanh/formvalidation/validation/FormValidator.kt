package com.khanh.formvalidation.validation

import android.util.Patterns
import com.khanh.formvalidation.R

object FormValidator {
    private val vietnamesePhonePattern = Regex("^(03|05|07|08|09)\\d{8}$")
    private val whitespacePattern = Regex("\\s+")

    fun validateFullName(value: String): ValidationResult {
        val normalizedName = normalizeFullName(value)
        return when {
            normalizedName.isBlank() -> invalid(R.string.error_name_required)
            normalizedName.split(whitespacePattern).size < 2 -> invalid(R.string.error_name_invalid)
            else -> valid()
        }
    }

    fun validateEmail(value: String): ValidationResult {
        val email = value.trim()
        return when {
            email.isBlank() -> invalid(R.string.error_email_required)
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> invalid(R.string.error_email_invalid)
            else -> valid()
        }
    }

    fun validatePassword(value: String): ValidationResult = when {
        value.isBlank() -> invalid(R.string.error_password_required)
        value.length < MINIMUM_PASSWORD_LENGTH -> invalid(R.string.error_password_short)
        else -> valid()
    }

    fun validatePhoneNumber(value: String): ValidationResult {
        val phoneNumber = value.trim()
        return when {
            phoneNumber.isBlank() -> invalid(R.string.error_phone_required)
            !vietnamesePhonePattern.matches(phoneNumber) -> invalid(R.string.error_phone_invalid)
            else -> valid()
        }
    }

    fun validateTerms(isAccepted: Boolean): ValidationResult =
        if (isAccepted) valid() else invalid(R.string.error_terms_required)

    fun normalizeFullName(value: String): String =
        value.trim().replace(whitespacePattern, " ")

    private fun valid() = ValidationResult(isValid = true)

    private fun invalid(errorMessageResId: Int) = ValidationResult(
        isValid = false,
        errorMessageResId = errorMessageResId
    )

    private const val MINIMUM_PASSWORD_LENGTH = 6
}
