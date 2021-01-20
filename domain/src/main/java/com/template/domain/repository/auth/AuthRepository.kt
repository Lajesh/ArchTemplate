package com.template.domain.repository.auth

import com.template.domain.common.ResultState
import com.template.domain.entity.auth.AuthEntity
import com.template.domain.repository.BaseRepository
import io.reactivex.Single

/**
 * Keep all the service calls related to Authenticaion here
 * Created by Lajesh Dineshkumar on 2020-03-09.
 * Company: Nagarro Middle East
 * Email: lajesh.dineshkumar@nagarro.com
 */
interface AuthRepository : BaseRepository {

    fun signIn(userId: String, password: String): Single<ResultState<AuthEntity.LoginResponse>>

}
