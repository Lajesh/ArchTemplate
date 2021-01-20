package com.template.data.datasource.remote.api

import com.template.data.datasource.remote.dto.FitnessDataDto
import io.reactivex.Single
import retrofit2.http.GET

/*******
 * Keep all fitness data related APIs here
 * Author: Lajesh Dineshkumar
 * Created on: 16/10/2020
 * Modified on: 16/10/2020
 ********/
interface IFitnessApi {
    @GET("retrieve-heartrate-variablity")
    fun getHRVariablitiy(): Single<FitnessDataDto.HRVariabilityResponse>
}