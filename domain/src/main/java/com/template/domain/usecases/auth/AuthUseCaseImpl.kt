package com.template.domain.usecases.auth

import com.template.domain.common.ResultState
import com.template.domain.entity.auth.AuthEntity
import com.template.domain.repository.auth.AuthRepository
import io.reactivex.Single

/**
 * Implementation of authentication use cases
 * Created by Lajesh Dineshkumar on 2020-03-09.
 * Company: Nagarro Middle East
 * Email: lajesh.dineshkumar@nagarro.com
 */
class AuthUseCaseImpl(private val authRepository: AuthRepository) : AuthUseCase {

    override fun signIn(
        userId: String,
        password: String
    ): Single<ResultState<AuthEntity.LoginResponse>> {
        return authRepository.signIn(userId, password)
    }

}
