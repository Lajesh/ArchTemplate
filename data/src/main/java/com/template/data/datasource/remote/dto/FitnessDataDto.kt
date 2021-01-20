package com.template.data.datasource.remote.dto

import com.google.gson.annotations.SerializedName

/*******
 * File Description
 * Author: Lajesh Dineshkumar
 * Created on: 16/10/2020
 * Modified on: 16/10/2020
 ********/

sealed class FitnessDataDto {
    data class HRVariabilityResponse(
        @SerializedName("data") val data : List<HRVariability>
    )

    data class HRVariability(
        @SerializedName("date") val date : String? = "",
        @SerializedName("value") val value : Int = 0
    ) : FitnessDataDto()

}