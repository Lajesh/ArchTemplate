package com.template.cleanapp.di.application

import com.template.data.repository.auth.AuthRepositoryImpl
import com.template.data.repository.fitnessdata.FitnessDataRepositoryImpl
import com.template.domain.repository.auth.AuthRepository
import com.template.domain.repository.fitnessdata.FitnessDataRepository
import org.koin.dsl.module

/**
 * Repository Module
 * Created by Lajesh Dineshkumar on 10/30/2019.
 * Email: lajesh.dineshkumar@nagarro.com
 */

val repositoryModule = module {

    single<AuthRepository> {
        AuthRepositoryImpl(api = get())
    }

    single<FitnessDataRepository> {
        FitnessDataRepositoryImpl(api = get())
    }

}
