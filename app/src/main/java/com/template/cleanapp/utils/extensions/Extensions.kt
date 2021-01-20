package com.template.cleanapp.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.math.BigInteger
import java.security.MessageDigest
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/****
 * Keep all extension functions here
 * Author: Lajesh Dineshkumar
 * Company: Farabi Technologies
 * Created on: 2020-04-30
 * Modified on: 2020-04-30
 *****/

fun Map<String, String>.bundle(): Bundle {
    val bundle = Bundle()
    for (entry in this.entries)
        bundle.putString(entry.key.replace(" ", "_"), entry.value)
    return bundle
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Boolean?.orDefault(default: Boolean = false): Boolean {
    if (this == null)
        return default
    return this
}

fun Int.toPx(unit: Int = TypedValue.COMPLEX_UNIT_DIP): Int =
    this.toFloat().toPx(unit).toInt()

fun Int.toDp(): Int = this.toFloat().toDp().toInt()

fun Float.toPx(unit: Int = TypedValue.COMPLEX_UNIT_DIP): Float =
    (TypedValue.applyDimension(unit, this, Resources.getSystem().displayMetrics))

fun Float.toDp(): Float = (this / Resources.getSystem().displayMetrics.density)

fun Long.format(format: String? = "00"): String {
    return (NumberFormat.getNumberInstance(Locale.US) as? DecimalFormat)?.apply {
        this.applyPattern(format)
    }?.format(this) ?: ""
}

fun String.getHash(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

inline fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

inline fun <T1 : Any, T2 : Any, T3 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    block: (T1, T2, T3) -> R?
): R? {
    return if (p1 != null && p2 != null && p3 != null) block(p1, p2, p3) else null
}

inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    p4: T4?,
    block: (T1, T2, T3, T4) -> R?
): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null) block(p1, p2, p3, p4) else null
}

inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    p4: T4?,
    p5: T5?,
    block: (T1, T2, T3, T4, T5) -> R?
): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null && p5 != null) block(
        p1,
        p2,
        p3,
        p4,
        p5
    ) else null
}

fun Double?.isGreaterThan(other: Double?) =
    this != null && other != null && this >= other

fun Double?.isLessThan(other: Double?) =
    this != null && other != null && this <= other

fun String.capitalizeWords(): String =
    split(" ").map { it.capitalize() }.joinToString(" ")
