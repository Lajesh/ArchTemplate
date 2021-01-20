package com.template.cleanapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.template.domain.entity.auth.AuthEntity
import com.template.domain.entity.fitnessdata.FitnessDataEntity
import com.template.cleanapp.utils.StringResolver


/****
 * Shared viewmodel class. This viewmodel is in activity scope.
 * So we can use this to share data between fragments.
 * Author: Lajesh Dineshkumar
 * Company: Farabi Technologies
 * Created on: 2020-03-03
 * Modified on: 2020-03-03
 *****/
open class SharedViewModel constructor(private val stringResolver: StringResolver) : ViewModel() {

    val userDetails = MutableLiveData<AuthEntity.UserDetails>()
    var hrVariabilityData: List<FitnessDataEntity.HRVariablity> = emptyList()

}
