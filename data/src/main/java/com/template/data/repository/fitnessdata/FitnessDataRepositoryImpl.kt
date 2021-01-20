package com.template.data.repository.fitnessdata

import com.template.data.datasource.mapper.dtotoentity.map
import com.template.data.datasource.remote.api.IFitnessApi
import com.template.data.extension.applyIoScheduler
import com.template.data.repository.BaseRepositoryImpl
import com.template.domain.common.ResultState
import com.template.domain.entity.fitnessdata.FitnessDataEntity
import com.template.domain.repository.fitnessdata.FitnessDataRepository
import io.reactivex.Single

/*******
 * File Description
 * Author: Lajesh Dineshkumar
 * Created on: 16/10/2020
 * Modified on: 16/10/2020
 ********/
class FitnessDataRepositoryImpl(val api: IFitnessApi) : BaseRepositoryImpl(),
FitnessDataRepository{
    override fun getHRVariability(): Single<ResultState<FitnessDataEntity.HRVariablityResponse>> {
        return api.getHRVariablitiy().applyIoScheduler().map {
            ResultState.Success(it.map()) as ResultState<FitnessDataEntity.HRVariablityResponse>
        }.onErrorReturn {
            handleError(it) as ResultState<FitnessDataEntity.HRVariablityResponse>
        }

    }
}
