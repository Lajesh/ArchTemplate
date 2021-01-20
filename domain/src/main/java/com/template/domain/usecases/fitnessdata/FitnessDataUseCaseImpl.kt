package com.template.domain.usecases.fitnessdata

import com.template.domain.common.ResultState
import com.template.domain.entity.fitnessdata.FitnessDataEntity
import com.template.domain.repository.fitnessdata.FitnessDataRepository
import io.reactivex.Single

/**
 * Implementation of FitnessData use cases
 * Created by Lajesh Dineshkumar on 2020-03-09.
 * Company: Nagarro Middle East
 * Email: lajesh.dineshkumar@nagarro.com
 */
class FitnessDataUseCaseImpl(private val fitnessDataRepository: FitnessDataRepository) : FitnessDataUseCase {
    override fun getHRVariability(): Single<ResultState<FitnessDataEntity.HRVariablityResponse>> {
        return fitnessDataRepository.getHRVariability()
    }


}
