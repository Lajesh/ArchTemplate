package com.template.domain.repository.fitnessdata

import com.template.domain.common.ResultState
import com.template.domain.entity.fitnessdata.FitnessDataEntity
import io.reactivex.Single

/*******
 * FitnessDataRepository
 * Author: Lajesh Dineshkumar
 * Created on: 16/10/2020
 * Modified on: 16/10/2020
 ********/
interface FitnessDataRepository {
    fun getHRVariability() : Single<ResultState<FitnessDataEntity.HRVariablityResponse>>
}