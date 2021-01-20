package com.template.cleanapp.config

import com.template.cleanapp.BuildConfig

/****
 * Keep all the common configurations here
 * Author: Lajesh Dineshkumar
 * Created on: 16/10/2020
 * Modified on: 16/10/2020
 *****/
object Configuration {
    // Deployment Types
    private const val DEV = "dev"
    private const val SIT = "sit"
    private const val UAT = "uat"
    private const val PROD = "prod"
    private const val PILOT = "pilot"

    // Host URLs
    private const val DEV_URL = "https://demo4597733.mockable.io/"
    private const val SIT_URL = "https://demo4597733.mockable.io/sit/"
    private const val UAT_URL = "https://demo4597733.mockable.io/uat/"
    private const val PROD_URL = "https://com.doc.mhealth/"
    private const val PILOT_URL = "https://demo4597733.mockable.io/pilot/"

    const val CONNECT_TIMEOUT: Long = 120
    const val READ_TIMEOUT: Long = 120
    const val CALL_TIMEOUT: Long = 120

    val baseURL: String
        get() {

            return when (BuildConfig.FLAVOR) {

                DEV -> DEV_URL

                SIT -> SIT_URL

                UAT -> UAT_URL

                PROD -> PROD_URL

                PILOT -> PILOT_URL

                else -> DEV_URL
            }
        }


}
