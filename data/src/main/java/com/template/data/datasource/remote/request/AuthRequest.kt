package com.template.data.datasource.remote.request

import com.google.gson.annotations.SerializedName

/**
 * Authentication request model class
 * Created by Lajesh Dineshkumar on 2020-03-09.
 * Company: Nagarro Middle East
 * Email: lajesh.dineshkumar@nagarro.com
 */
sealed class AuthRequest {

    abstract val userId: String
    abstract val password: String

    data class LoginRequest(
        @SerializedName("username") override val userId: String,
        @SerializedName("password") override val password: String
    ) : AuthRequest()
}
