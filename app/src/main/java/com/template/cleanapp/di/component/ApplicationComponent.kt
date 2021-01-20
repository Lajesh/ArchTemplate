package com.template.cleanapp.di.component

import com.template.cleanapp.di.application.*
import com.template.cleanapp.di.viewmodel.viewModelModule
import org.koin.core.module.Module

/**
 * Application component
 * Created by Lajesh Dineshkumar on 10/28/2019.
 * Company: Nagarro Middle East
 * Email: lajesh.dineshkumar@nagarro.com
 */

val appComponent: List<Module> = listOf(
    appModule, retrofitModule,
    okhttpModule, repositoryModule, useCaseModule,
    apiModule, viewModelModule
)
