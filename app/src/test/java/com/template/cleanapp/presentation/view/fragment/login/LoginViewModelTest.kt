package com.template.cleanapp.presentation.view.fragment.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.template.domain.usecases.auth.AuthUseCase
import com.template.domain.usecases.fitnessdata.FitnessDataUseCase
import com.template.cleanapp.R
import com.template.cleanapp.base.BaseUnitTest
import com.template.cleanapp.presentation.viewmodel.SharedViewModel
import com.template.cleanapp.utils.MockResponse
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.test.KoinTest
import org.koin.test.inject
import org.powermock.api.mockito.PowerMockito
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.modules.junit4.PowerMockRunnerDelegate
import java.net.HttpURLConnection

/*******
 * LoginViewModel Test
 * Author: Lajesh Dineshkumar
 * Created on: 17/10/2020
 * Modified on: 17/10/2020
 ********/
@RunWith(PowerMockRunner::class)
@PowerMockRunnerDelegate(JUnit4::class)
class LoginViewModelTest: BaseUnitTest(), KoinTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var loginViewModel: LoginViewModel
    private val authUseCase: AuthUseCase by inject()
    private val fitnessDataUseCase: FitnessDataUseCase by inject()

    @Before
    override fun setUp() {
        super.setUp()
        mockStrings()
        loginViewModel = LoginViewModel(stringResolver, authUseCase, fitnessDataUseCase)
        loginViewModel.sharedViewModel = SharedViewModel(stringResolver)
    }

    @Test
    fun testViewModelInitialized() {
        Assert.assertNotNull(loginViewModel)
    }


    @Test
    fun onPasswordChangeValid() {
        loginViewModel.password.postValue("Ushushdn")
        loginViewModel.onPasswordFocusChangeListener.onFocusChange(null, false)
        Assert.assertEquals(true, loginViewModel.passwordErrorText.value.isNullOrEmpty())
    }

    @Test
    fun onPasswordChangeInValid() {
        loginViewModel.password.postValue("")
        loginViewModel.onPasswordFocusChangeListener.onFocusChange(null, false)
        Assert.assertEquals(false, loginViewModel.passwordErrorText.value.isNullOrEmpty())
    }

    @Test
    fun onEmailChangeInValidEmpty(){
        loginViewModel.userEmail.value = ""
        loginViewModel.onEmailFocusChangeListener.onFocusChange(null, false)
        Assert.assertEquals(false, loginViewModel.emailErrorText.value.isNullOrEmpty())
    }

    @Test
    fun onEmailChangeInValid(){
        loginViewModel.userEmail.value = "sdsd"
        loginViewModel.onEmailFocusChangeListener.onFocusChange(null, false)
        Assert.assertEquals(false, loginViewModel.emailErrorText.value.isNullOrEmpty())
    }

    @Test
    fun onEmailChangeValid(){
        loginViewModel.userEmail.value = "lajeshds2007@gmail.com"
        loginViewModel.onEmailFocusChangeListener.onFocusChange(null, false)
        Assert.assertEquals(true, loginViewModel.emailErrorText.value.isNullOrEmpty())
    }

    @Test
    fun onLoginSuccess(){
        mockWebServer.enqueue(
            MockResponse.createMockResponse(
                "login_response",
                HttpURLConnection.HTTP_OK
            )
        )
        mockWebServer.enqueue(
            MockResponse.createMockResponse(
                "hr_variability_response",
                HttpURLConnection.HTTP_OK
            )
        )
        loginViewModel.userEmail.value = "lajeshds2007@gmail.com"
        loginViewModel.password.value = "123455"
        loginViewModel.doLogin()
        loginViewModel.sharedViewModel.userDetails.observeForever {
            Assert.assertNotNull(it)
        }

    }

    @Test
    fun onLoginSuccessFitnessDataFailure(){
        mockWebServer.enqueue(
            MockResponse.createMockResponse(
                "login_response",
                HttpURLConnection.HTTP_OK
            )
        )
        mockWebServer.enqueue(
            MockResponse.createMockResponse(
                "hr_variability_response",
                HttpURLConnection.HTTP_BAD_GATEWAY
            )
        )
        loginViewModel.userEmail.value = "lajeshds2007@gmail.com"
        loginViewModel.password.value = "123455"
        loginViewModel.doLogin()
        loginViewModel.sharedViewModel.userDetails.observeForever {
            Assert.assertNotNull(it)
        }
    }

    @Test
    fun onLoginFailure() {
        mockWebServer.enqueue(
            MockResponse.createMockResponse(
                "login_response",
                HttpURLConnection.HTTP_BAD_GATEWAY
            )
        )
        loginViewModel.userEmail.value = "lajeshds2007@gmail.com"
        loginViewModel.password.value = "123455"
        loginViewModel.doLogin()
        loginViewModel.sharedViewModel.userDetails.observeForever {
            Assert.assertNull(it)
            mockWebServer.enqueue(
                MockResponse.createMockResponse(
                    "hr_variability_response",
                    HttpURLConnection.HTTP_OK
                )
            )
        }
    }

    @Test
    fun onForgotPassword(){
        loginViewModel.onForgotPassword()
    }

    private fun mockStrings() {
        PowerMockito.`when`(stringResolver.getString(R.string.error_enter_email))
            .thenReturn("Please enter your email address")
        PowerMockito.`when`(stringResolver.getString(R.string.error_enter_password))
            .thenReturn("Please enter password")
        PowerMockito.`when`(stringResolver.getString(R.string.error_email_invalid))
            .thenReturn("Please enter a valid email address")

    }
}