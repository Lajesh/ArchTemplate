package com.template.cleanapp.utils

import android.content.Context
import android.graphics.Typeface
import androidx.annotation.ArrayRes
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat

/*******
 * File Description
 * Author: Lajesh Dineshkumar
 * Created on: 16/10/2020
 * Modified on: 16/10/2020
 ********/
class StringResolver(private val context: Context) {

    /**
     * String resolver for viewModel
     */
    fun getString(@StringRes int: Int): String? {
        return try {
            context.getString(int)
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * String concat resolver for viewModel
     */
    fun getString(@StringRes int: Int, vararg params: String): String? {
        return try {
            context.getString(int, *params)
        } catch (e: Exception) {
            ""
        }
    }

    fun getStringArray(@ArrayRes int: Int): Array<String> {
        return try {
            context.resources.getStringArray(int)
        } catch (e: Exception) {
            arrayOf()
        }
    }

    /**
     * Typeface for the particular font
     */
    fun getTypeface(@FontRes font: Int): Typeface {
        return ResourcesCompat.getFont(context, font) ?: Typeface.DEFAULT
    }
}
