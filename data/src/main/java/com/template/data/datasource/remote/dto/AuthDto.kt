package com.template.data.datasource.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Keep all the DTOs related to authentication here
 * Created by Lajesh Dineshkumar on 2020-03-09.
 * Company: Nagarro Middle East
 * Email: lajesh.dineshkumar@nagarro.com
 */
sealed class AuthDto {
    data class LoginResponse(
        @SerializedName("userDetail") val userDetails: UserDetails? = null
    )

    data class UserDetails(
        @SerializedName("firstName") val firstName: String? = "",
        @SerializedName("lastName") val lastName: String? = ""
    ) : AuthDto()

}
