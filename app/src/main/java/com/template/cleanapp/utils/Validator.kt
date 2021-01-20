package com.template.cleanapp.utils

import com.template.cleanapp.common.Constants
import java.util.regex.Pattern

/*******
 * Validator class
 * Author: Lajesh Dineshkumar
 * Created on: 16/10/2020
 * Modified on: 16/10/2020
 ********/
object Validator {
    /**
     * Method which checks the string equality
     */
    fun validateStringEquality(string1: String?, string2: String?): Boolean {
        if (string1.isNullOrEmpty() || string1 != string2) {
            return false
        }
        return true
    }

    /**
     * Method which matches the string using regex
     */
    fun matchStringWithRegex(s: String?, regex: String): Boolean {
        if (s.isNullOrEmpty()) return false

        return Pattern.compile(regex).matcher(s).find()
    }

    /**
     * Method which checks if string has minimum valid length
     */
    fun hasMinimumLength(value: String?, minLength: Int): Boolean {
        value?.let {
            return value.length >= minLength
        }
        return false
    }

    fun checkIfValidEmail(email: String?): Boolean {
        if (email.isNullOrEmpty()) {
            return false
        }
        return Pattern.compile(Constants.EMAIL_ADDRESS_REGEX).matcher(email).find()

    }
}
