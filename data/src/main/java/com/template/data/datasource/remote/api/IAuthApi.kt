package com.template.data.datasource.remote.api


import com.template.data.datasource.remote.dto.AuthDto
import com.template.data.datasource.remote.request.AuthRequest
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Keep all the authentication related API in this endpoint
 * Created by Lajesh Dineshkumar on 2020-03-09.
 * Company: Nagarro Middle East
 * Email: lajesh.dineshkumar@nagarro.com
 */
interface IAuthApi {


    @POST("authenticate-user")
    fun signin(@Body loginrequest: AuthRequest.LoginRequest): Single<AuthDto.LoginResponse>

}
