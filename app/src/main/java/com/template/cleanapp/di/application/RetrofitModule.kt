package com.template.cleanapp.di.application

import com.template.cleanapp.config.Configuration
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit Module
 * Created by Lajesh Dineshkumar on 10/30/2019.
 * Company: Nagarro Middle East
 * Email: lajesh.dineshkumar@nagarro.com
 */

val retrofitModule = module {
    single {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(getGsonConverterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).baseUrl(get<String>())
            .build() as Retrofit
    }

    single {
        Configuration.baseURL
    }
}

fun getGsonConverterFactory(): GsonConverterFactory {
    return GsonConverterFactory.create(getGson())
}

fun getGson(): Gson {
    val gsonBuilder = GsonBuilder()
    gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    return gsonBuilder.create()
}
