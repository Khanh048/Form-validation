package com.khanh.formvalidation.model

import java.io.Serializable

data class User(
    val fullName: String,
    val email: String,
    val password: String,
    val phoneNumber: String
) : Serializable {
    private companion object {
        private const val serialVersionUID = 1L
    }
}
