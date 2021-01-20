@file:Suppress("UNCHECKED_CAST")

package com.template.data.repository.auth

import com.template.data.datasource.mapper.dtotoentity.map
import com.template.data.datasource.remote.api.IAuthApi
import com.template.data.datasource.remote.request.AuthRequest
import com.template.data.extension.applyIoScheduler
import com.template.data.repository.BaseRepositoryImpl
import com.template.domain.common.ResultState
import com.template.domain.entity.auth.AuthEntity
import com.template.domain.repository.auth.AuthRepository
import io.reactivex.Single

/**
 * AuthRepository Implementation
 * Created by Lajesh Dineshkumar on 2020-03-09.
 * Company: Nagarro Middle East
 * Email: lajesh.dineshkumar@nagarro.com
 */
class AuthRepositoryImpl(val api: IAuthApi) :
    BaseRepositoryImpl(),
    AuthRepository {

    override fun signIn(userId: String, password: String):
            Single<ResultState<AuthEntity.LoginResponse>> {
        return api.signin(
            AuthRequest.LoginRequest(
                userId, password
            )
        ).applyIoScheduler().map {
                ResultState.Success(it.map()) as ResultState<AuthEntity.LoginResponse>
            }.onErrorReturn {
                handleError(it) as ResultState<AuthEntity.LoginResponse>
            }
    }

}
