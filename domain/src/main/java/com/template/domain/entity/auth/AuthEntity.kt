package com.template.domain.entity.auth

/**
 * Keep all the models related to authentication here
 * Created by Lajesh Dineshkumar on 2020-03-09.
 * Company: Nagarro Middle East
 * Email: lajesh.dineshkumar@nagarro.com
 */
sealed class AuthEntity {

    data class LoginResponse(
        val userDetails: UserDetails? = null
    )

    data class UserDetails(
        val avatarUrl: String? = "",
        val firstName: String? = "",
        val lastName: String? = ""
    )

}
