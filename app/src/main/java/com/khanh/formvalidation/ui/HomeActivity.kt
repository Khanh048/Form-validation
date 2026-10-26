package com.khanh.formvalidation.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.khanh.formvalidation.R
import com.khanh.formvalidation.databinding.ActivityHomeBinding
import com.khanh.formvalidation.model.User

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegisterAnother.setOnClickListener { finish() }

        val user = readUser()
        if (user == null) {
            showMissingUserError()
            return
        }
        displayUser(user)
    }

    private fun displayUser(user: User) = with(binding) {
        tvGreeting.text = getString(R.string.home_greeting, user.fullName)
        tvNameValue.text = user.fullName
        tvEmailValue.text = user.email
        tvPhoneValue.text = user.phoneNumber
    }

    private fun showMissingUserError() {
        Snackbar.make(binding.root, R.string.error_user_missing, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.action_back) { finish() }
            .show()
    }

    @Suppress("DEPRECATION")
    private fun readUser(): User? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        intent.getSerializableExtra(EXTRA_USER, User::class.java)
    } else {
        intent.getSerializableExtra(EXTRA_USER) as? User
    }

    companion object {
        private const val EXTRA_USER = "com.khanh.formvalidation.extra.USER"

        fun createIntent(context: Context, user: User): Intent =
            Intent(context, HomeActivity::class.java).putExtra(EXTRA_USER, user)
    }
}
