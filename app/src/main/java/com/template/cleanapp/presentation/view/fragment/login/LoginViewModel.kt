package com.template.cleanapp.presentation.view.fragment.login

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.template.cleanapp.R
import com.template.domain.common.ResultState
import com.template.domain.usecases.auth.AuthUseCase
import com.template.domain.usecases.fitnessdata.FitnessDataUseCase
import com.template.cleanapp.architecture.SingleLiveEvent
import com.template.cleanapp.presentation.viewmodel.BaseViewModel
import com.template.cleanapp.utils.StringResolver
import com.template.cleanapp.utils.Validator

/*******
 * Login ViewModel
 * Author: Lajesh Dineshkumar
 * Created on: 16/10/2020
 * Modified on: 16/10/2020
 ********/
class LoginViewModel constructor(
    private val stringResolver: StringResolver,
    private val authUseCase: AuthUseCase,
    private val fitnessDataUseCase: FitnessDataUseCase
) : BaseViewModel() {

    val emailErrorText = MutableLiveData<String>()
    val passwordErrorText = MutableLiveData<String>()
    val userEmail = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val loginSuccessEvent = SingleLiveEvent<Void>()

    val onPasswordFocusChangeListener = View.OnFocusChangeListener { _, isFocused ->
        if (!isFocused) {
            validatePassword()
        }
    }

    val onEmailFocusChangeListener = View.OnFocusChangeListener { _, isFocused ->
        if (!isFocused) {
            validateEmail()
        }
    }

    private fun validateEmail(): Boolean {
        return if (userEmail.value.isNullOrEmpty()) {
            emailErrorText.value = stringResolver.getString(R.string.error_enter_email)
            false
        } else if (!Validator.checkIfValidEmail(userEmail.value)) {
            emailErrorText.value = stringResolver.getString(R.string.error_email_invalid)
            false
        } else {
            emailErrorText.value = ""
            true
        }
    }

    private fun validatePassword(): Boolean {
        return if (password.value.isNullOrEmpty()) {
            passwordErrorText.value = stringResolver.getString(R.string.error_enter_password)
            false
        } else {
            passwordErrorText.value = ""
            true
        }
    }

    private fun validateAllFields(): Boolean {
        val isValidEmail = validateEmail()
        val isValidPassword = validatePassword()
        return isValidEmail && isValidPassword
    }

    fun doLogin() {
        if (validateAllFields()) {
            showLoading(true)
            authUseCase.signIn(userEmail.value.toString(), password.value.toString()).toFlowable()
                .subscribe {
                    when (it) {
                        is ResultState.Success -> {
                            sharedViewModel.userDetails.value = it.data.userDetails
                            getFitnessData()
                        }
                        is ResultState.Error -> {
                            setError(it.error)
                            showLoading(false)
                        }
                    }
                }.track()
        }
    }

    private fun getFitnessData() {
        showLoading(true)
        fitnessDataUseCase.getHRVariability().toFlowable()
            .subscribe {
                when (it) {
                    is ResultState.Success -> {
                        sharedViewModel.hrVariabilityData = it.data.dataList
                        loginSuccessEvent.call()
                        showLoading(false)
                    }
                    is ResultState.Error -> {
                        setError(it.error)
                        showLoading(false)
                    }
                }
            }.track()
    }

    fun onForgotPassword() {
        // Implement Later
    }
}