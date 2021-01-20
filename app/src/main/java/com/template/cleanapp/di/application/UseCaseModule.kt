package com.template.cleanapp.di.application

import com.template.domain.usecases.auth.AuthUseCase
import com.template.domain.usecases.auth.AuthUseCaseImpl
import com.template.domain.usecases.fitnessdata.FitnessDataUseCase
import com.template.domain.usecases.fitnessdata.FitnessDataUseCaseImpl
import org.koin.dsl.module

/**
 * UseCase Module
 * Created by Lajesh Dineshkumar on 10/30/2019.
 * Company: Nagarro Middle East
 * Email: lajesh.dineshkumar@nagarro.com
 */
val useCaseModule = module {
    factory<AuthUseCase> {
        AuthUseCaseImpl(authRepository = get())
    }

    factory<FitnessDataUseCase> {
        FitnessDataUseCaseImpl(fitnessDataRepository = get())
    }

}
