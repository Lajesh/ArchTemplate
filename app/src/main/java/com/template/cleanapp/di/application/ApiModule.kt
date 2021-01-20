package com.template.cleanapp.di.application

import com.template.data.datasource.remote.api.IAuthApi
import com.template.data.datasource.remote.api.IFitnessApi
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * API Module
 * Created by Lajesh Dineshkumar on 10/30/2019.
 * Email: lajesh.dineshkumar@nagarro.com
 */
val apiModule = module {

    single {
        get<Retrofit>().create(IAuthApi::class.java)
    }

    single {
        get<Retrofit>().create(IFitnessApi::class.java)
    }

}
