package com.template.cleanapp.utils

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.template.cleanapp.CleanApplication
import com.template.cleanapp.utils.extensions.hideKeyboard

/*******
 * Keep all static binding adapters here
 * Author: Lajesh Dineshkumar
 * Created on: 16/10/2020
 * Modified on: 16/10/2020
 ********/
object BindingAdapter {
    /**
     * Setting visibility for Views
     */
    @JvmStatic
    @BindingAdapter("visibility")
    fun setVisibility(
        view: View,
        visibility: Boolean?
    ) {
        if (null != visibility) {
            view.visibility = if (visibility) {
                CleanApplication.applicationContext().hideKeyboard(view)
                View.VISIBLE
            } else
                View.GONE
        } else {
            view.visibility = View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("error")
    fun setError(til: TextInputLayout, error: String?) {
        error?.let {
            til.error = it
            til.isErrorEnabled = it.isNotBlank()
        }
    }
}