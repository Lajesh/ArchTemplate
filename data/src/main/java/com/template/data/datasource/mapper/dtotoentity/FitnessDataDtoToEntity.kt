package com.template.data.datasource.mapper.dtotoentity

import com.template.data.datasource.remote.dto.FitnessDataDto
import com.template.domain.entity.fitnessdata.FitnessDataEntity

/*******
 * FitnessDataDto to Entity
 * Author: Lajesh Dineshkumar
 * Created on: 16/10/2020
 * Modified on: 16/10/2020
 ********/

fun FitnessDataDto.HRVariabilityResponse.map() = FitnessDataEntity.HRVariablityResponse(
    dataList = data.map { it.map() }
)

fun FitnessDataDto.HRVariability.map() = FitnessDataEntity.HRVariablity(
    date = date,
    value = value
)