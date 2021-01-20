package com.template.data.datasource.mapper.dtotoentity

import com.template.data.datasource.remote.dto.AuthDto
import com.template.domain.entity.auth.AuthEntity


/**
 * Keep all the DTO to Entity mapping for authentication here
 * Created by Lajesh Dineshkumar on 2020-03-09.
 * Company: Nagarro Middle East
 * Email: lajesh.dineshkumar@nagarro.com
 */

fun AuthDto.LoginResponse.map() = AuthEntity.LoginResponse(
    userDetails = userDetails?.map()
)

fun AuthDto.UserDetails.map() = AuthEntity.UserDetails(
    firstName = firstName,
    lastName = lastName
)

