package com.khanh.formvalidation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.khanh.formvalidation.R
import com.khanh.formvalidation.databinding.ActivityRegistrationBinding
import com.khanh.formvalidation.model.User
import com.khanh.formvalidation.validation.FormValidator
import com.khanh.formvalidation.validation.ValidationResult
import java.util.Locale

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRealtimeValidation()
        binding.btnRegister.setOnClickListener { registerUser() }
        binding.btnCancel.setOnClickListener { clearForm() }
    }

    private fun setupRealtimeValidation() = with(binding) {
        etName.doAfterTextChanged { tilName.error = null }
        etEmail.doAfterTextChanged { tilEmail.error = null }
        etPassword.doAfterTextChanged { tilPassword.error = null }
        etPhone.doAfterTextChanged { tilPhone.error = null }
        cbTerms.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                button.error = null
                tvTermsError.visibility = View.GONE
            }
        }
    }

    private fun registerUser() = with(binding) {
        val fullName = etName.text?.toString().orEmpty()
        val email = etEmail.text?.toString().orEmpty()
        val password = etPassword.text?.toString().orEmpty()
        val phoneNumber = etPhone.text?.toString().orEmpty()

        val nameResult = FormValidator.validateFullName(fullName)
        val emailResult = FormValidator.validateEmail(email)
        val passwordResult = FormValidator.validatePassword(password)
        val phoneResult = FormValidator.validatePhoneNumber(phoneNumber)
        val termsResult = FormValidator.validateTerms(cbTerms.isChecked)

        tilName.showValidation(nameResult)
        tilEmail.showValidation(emailResult)
        tilPassword.showValidation(passwordResult)
        tilPhone.showValidation(phoneResult)
        showTermsValidation(termsResult)

        val isFormValid = listOf(
            nameResult,
            emailResult,
            passwordResult,
            phoneResult,
            termsResult
        ).all(ValidationResult::isValid)

        if (!isFormValid) {
            focusFirstInvalidField(nameResult, emailResult, passwordResult, phoneResult, termsResult)
            Snackbar.make(root, R.string.error_form_invalid, Snackbar.LENGTH_LONG).show()
            return
        }

        val user = User(
            fullName = FormValidator.normalizeFullName(fullName),
            email = email.trim().lowercase(Locale.ROOT),
            password = password,
            phoneNumber = phoneNumber.trim()
        )
        hideKeyboard()
        startActivity(HomeActivity.createIntent(this@RegistrationActivity, user))
    }

    private fun TextInputLayout.showValidation(result: ValidationResult) {
        error = result.errorMessageResId?.let(::getString)
    }

    private fun showTermsValidation(result: ValidationResult) = with(binding) {
        if (result.isValid) {
            cbTerms.error = null
            tvTermsError.visibility = View.GONE
        } else {
            val message = getString(requireNotNull(result.errorMessageResId))
            cbTerms.error = message
            tvTermsError.text = message
            tvTermsError.visibility = View.VISIBLE
        }
    }

    private fun focusFirstInvalidField(
        nameResult: ValidationResult,
        emailResult: ValidationResult,
        passwordResult: ValidationResult,
        phoneResult: ValidationResult,
        termsResult: ValidationResult
    ) {
        when {
            !nameResult.isValid -> binding.etName.requestFocus()
            !emailResult.isValid -> binding.etEmail.requestFocus()
            !passwordResult.isValid -> binding.etPassword.requestFocus()
            !phoneResult.isValid -> binding.etPhone.requestFocus()
            !termsResult.isValid -> binding.cbTerms.requestFocus()
        }
    }

    private fun clearForm() = with(binding) {
        etName.text?.clear()
        etEmail.text?.clear()
        etPassword.text?.clear()
        etPhone.text?.clear()
        cbTerms.isChecked = false

        tilName.error = null
        tilEmail.error = null
        tilPassword.error = null
        tilPhone.error = null
        cbTerms.error = null
        tvTermsError.visibility = View.GONE

        etName.requestFocus()
        hideKeyboard()
    }

    private fun hideKeyboard() {
        currentFocus?.let { focusedView ->
            getSystemService<InputMethodManager>()?.hideSoftInputFromWindow(
                focusedView.windowToken,
                0
            )
        }
    }
}
