package com.template.cleanapp.di

import com.google.gson.GsonBuilder
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/****
 * Network module test configuration with mockserver url.
 * Author: Lajesh Dineshkumar
 * Company: Farabi Technologies
 * Created on: 6/18/20
 * Modified on: 6/18/20
 *****/

fun configureNetworkModuleForTest(baseApi: String) = module {
    single {
        Retrofit.Builder()
            .baseUrl(baseApi)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}
