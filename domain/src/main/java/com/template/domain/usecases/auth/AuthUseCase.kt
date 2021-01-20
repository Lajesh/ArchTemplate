package com.template.domain.usecases.auth

import com.template.domain.common.ResultState
import com.template.domain.entity.auth.AuthEntity
import com.template.domain.usecases.BaseUseCase
import io.reactivex.Single

/**
 * Keep all the authentication related business use cases here
 * Created by Lajesh Dineshkumar on 2020-03-09.
 * Company: Nagarro Middle East
 * Email: lajesh.dineshkumar@nagarro.com
 */
interface AuthUseCase : BaseUseCase {
    fun signIn(userId: String, password: String): Single<ResultState<AuthEntity.LoginResponse>>
}
