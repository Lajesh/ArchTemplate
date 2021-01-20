package com.template.cleanapp.di.application

import com.template.cleanapp.CleanApplication
import com.template.cleanapp.utils.StringResolver
import org.koin.dsl.module

/****
 * Keep app wide dependencies here
 * Author: Lajesh Dineshkumar
 * Created on: 6/18/20
 * Modified on: 6/18/20
 *****/
val appModule = module {
    factory {
        StringResolver(CleanApplication.localeContext())
    }
}
