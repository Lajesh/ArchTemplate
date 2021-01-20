package com.template.cleanapp.di

import com.template.cleanapp.di.application.apiModule
import com.template.cleanapp.di.application.appModule
import com.template.cleanapp.di.application.repositoryModule
import com.template.cleanapp.di.application.useCaseModule

/****
 * File Description
 * Author: Lajesh Dineshkumar
 * Company: Farabi Technologies
 * Created on: 6/18/20
 * Modified on: 6/18/20
 *****/
fun configureTestAppComponent(baseApi: String) = listOf(
    MockWebServerDIPTest,
    configureNetworkModuleForTest(baseApi),
    apiModule,
    useCaseModule,
    repositoryModule,
    appModule
)
