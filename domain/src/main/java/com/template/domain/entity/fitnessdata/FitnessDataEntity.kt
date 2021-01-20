package com.template.domain.entity.fitnessdata

/*******
 * FitnessDataEntity
 * Author: Lajesh Dineshkumar
 * Created on: 16/10/2020
 * Modified on: 16/10/2020
 ********/
sealed class FitnessDataEntity {

    data class HRVariablityResponse(
        var dataList: List<HRVariablity> = emptyList()
    )

    data class HRVariablity(
        val date: String? = "",
        val value: Int = 0
    )
}