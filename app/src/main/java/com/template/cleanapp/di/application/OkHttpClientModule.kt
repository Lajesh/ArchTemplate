package com.template.cleanapp.di.application

import com.template.cleanapp.config.Configuration
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import java.net.CookieManager
import java.util.concurrent.TimeUnit
import com.template.cleanapp.BuildConfig

/**
 * Keep all OkHttp dependencies here
 * Created by Lajesh Dineshkumar on 10/30/2019.
 * Email: lajesh.dineshkumar@nagarro.com
 */

val okhttpModule = module {
    single {
        val cookieHandler = CookieManager()
        val okHttpBuilder = OkHttpClient()
            .newBuilder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .cookieJar(JavaNetCookieJar(cookieHandler))
            .connectTimeout(Configuration.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .callTimeout(Configuration.CALL_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Configuration.READ_TIMEOUT, TimeUnit.SECONDS)


        okHttpBuilder.build() as OkHttpClient
    }


    single {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE

        httpLoggingInterceptor
    }
}
