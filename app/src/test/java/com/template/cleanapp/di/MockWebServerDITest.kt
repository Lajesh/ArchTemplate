package com.template.cleanapp.di

import okhttp3.mockwebserver.MockWebServer
import org.koin.dsl.module

/****
 * MockWebServerDIPTest
 * Author: Lajesh Dineshkumar
 * Company: Farabi Technologies
 * Created on: 6/18/20
 * Modified on: 6/18/20
 *****/

val MockWebServerDIPTest = module {
    factory {
        MockWebServer()
    }
}
