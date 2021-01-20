package com.template.cleanapp

import android.app.Application
import android.content.Context
import com.template.cleanapp.di.AppInjector
import com.template.cleanapp.di.component.appComponent
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import org.koin.core.context.startKoin

/*******
 * Application class
 * Author: Lajesh Dineshkumar
 * Created on: 16/10/2020
 * Modified on: 16/10/2020
 ********/
class CleanApplication : Application() {

    private var localeContext: Context? = null

    init {
        instance = this
    }

    companion object {
        private lateinit var instance: CleanApplication

        private var isAppVisible: Boolean = false

        fun applicationContext(): Context {
            return instance.applicationContext
        }

        fun localeContext(): Context {
            return instance.localeContext ?: instance.applicationContext
        }

        fun getInstance(): CleanApplication? {
            return instance
        }

        fun setInstance(application: CleanApplication) {
            instance = application
        }

        fun isApplicationVisible(): Boolean {
            return isAppVisible
        }

        fun setAppVisible(isVisible: Boolean) {
            isAppVisible = isVisible
        }
    }

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)

        startKoin {
            modules(appComponent)
        }

        // Init viewpump
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/roboto_regular.ttf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )

    }
}
