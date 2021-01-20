package com.template.cleanapp.utils.extensions

import android.util.Log
import android.view.View
import androidx.annotation.IntDef
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.template.cleanapp.R
import com.template.cleanapp.utils.extensions.FragmentAnimation.Companion.TRANSITION_FADE_IN_OUT
import com.template.cleanapp.utils.extensions.FragmentAnimation.Companion.TRANSITION_FADE_IN_POP_OUT
import com.template.cleanapp.utils.extensions.FragmentAnimation.Companion.TRANSITION_NONE
import com.template.cleanapp.utils.extensions.FragmentAnimation.Companion.TRANSITION_POP
import com.template.cleanapp.utils.extensions.FragmentAnimation.Companion.TRANSITION_PUSH_TO_STACK
import com.template.cleanapp.utils.extensions.FragmentAnimation.Companion.TRANSITION_SLIDE_LEFT_RIGHT
import com.template.cleanapp.utils.extensions.FragmentAnimation.Companion.TRANSITION_SLIDE_LEFT_RIGHT_WITHOUT_EXIT
import timber.log.Timber

/****
 * Keep all fragment related extensions here
 * Author: Lajesh Dineshkumar
 * Company: Farabi Technologies
 * Created on: 6/11/20
 * Modified on: 6/11/20
 *****/

@IntDef(
    TRANSITION_POP, TRANSITION_SLIDE_LEFT_RIGHT,
    TRANSITION_FADE_IN_OUT, TRANSITION_SLIDE_LEFT_RIGHT_WITHOUT_EXIT,
    TRANSITION_NONE, TRANSITION_PUSH_TO_STACK, TRANSITION_FADE_IN_POP_OUT
)
annotation class FragmentAnimation {
    companion object {
        const val TRANSITION_POP = 0
        const val TRANSITION_SLIDE_LEFT_RIGHT = 1
        const val TRANSITION_FADE_IN_OUT = 2
        const val TRANSITION_SLIDE_LEFT_RIGHT_WITHOUT_EXIT = 3
        const val TRANSITION_NONE = 4
        const val TRANSITION_PUSH_TO_STACK = 5
        const val TRANSITION_FADE_IN_POP_OUT = 6
    }
}

/**
 * The method for adding a new fragment
 * @param fragment: Fragment to be added
 * @param id: Fragment container ID
 * @param addToBackStack: Flag indicating whether to add to backstack or not
 */
fun FragmentManager.addFragment(
    fragment: Fragment,
    id: Int,
    addToBackStack: Boolean,
    sharedElementTransition: Pair<View, String>? = null,
    @FragmentAnimation animationType: Int = FragmentAnimation.TRANSITION_NONE
) {
    val transaction = this.beginTransaction()
    if (sharedElementTransition != null) {
        transaction.addSharedElement(sharedElementTransition.first, sharedElementTransition.second)
        transaction.addToBackStack(fragment.javaClass.canonicalName)
    } else {
        applyFragmentTransition(animationType, transaction, addToBackStack, fragment)
    }
    transaction.add(id, fragment, fragment.javaClass.canonicalName)
    transaction.commit()
}

/**
 * Method returns the backstack entry count
 */
fun FragmentManager.getBackStackEntryCount(): Int {
    return this.backStackEntryCount
}

/**
 * The method for replacing a fragment allowing state loss
 * @param activity : Parent Activity
 * @param fragment: Fragment to be added
 * @param id: Fragment container ID
 * @param addToBackStack: Flag indicating whether to add to backstack or not
 * @param animationType: Fragment transition animation type
 */
fun AppCompatActivity.replaceFragmentAllowingStateLoss(
    fragment: Fragment,
    id: Int,
    addToBackStack: Boolean,
    @FragmentAnimation animationType: Int
) {
    val fragManager = this.supportFragmentManager
    val transaction = fragManager.beginTransaction()
    applyFragmentTransition(animationType, transaction, addToBackStack, fragment)
    transaction.replace(id, fragment, fragment.javaClass.canonicalName)
    transaction.commitAllowingStateLoss()
}

/**
 * The method for replacing a fragment
 * @param fragment: Fragment to be added
 * @param id: Fragment container ID
 * @param addToBackStack: Flag indicating whether to add to backstack or not
 * @param animationType: Fragment transition animation type
 */
fun FragmentManager.replaceFragment(
    fragment: Fragment,
    id: Int,
    addToBackStack: Boolean,
    @FragmentAnimation animationType: Int,
    vararg sharedElements: Pair<View, String>
) {
    val transaction = this.beginTransaction()
    if (sharedElements.isNotEmpty()) {
        sharedElements.forEach { pair ->
            val (view, tag) = pair
            transaction.addSharedElement(view, tag)
        }
        transaction.addToBackStack(fragment.javaClass.canonicalName)
    } else {
        applyFragmentTransition(animationType, transaction, addToBackStack, fragment)
    }
    transaction.replace(id, fragment, fragment.javaClass.canonicalName)
    transaction.commit()
}

/**
 * This method checks whether the specified fragment is the top fragment or not
 * @param activity: Parent Activity
 * @param fragmentTag : Fragment Tag Name
 */
fun AppCompatActivity.isCurrentTopFragment(fragmentTag: String): Boolean {
    val fragmentManager = this.supportFragmentManager
    if (fragmentManager.backStackEntryCount > 0) {
        val tag = fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1).name
        return fragmentTag.equals(tag, ignoreCase = true)
    }
    return false
}

/**
 * Returns current top fragment
 */
fun AppCompatActivity.getCurrentTopFragment(containerId: Int): Fragment? {
    val fragmentManager = this.supportFragmentManager
    if (fragmentManager.backStackEntryCount > 0) {
        return fragmentManager.findFragmentById(containerId)
    }
    return null
}

/**
 * This method checks whether the specified fragment is the top fragment or not
 * @param activity: Parent Activity
 * @param fragmentTag : Fragment Tag Name
 */
fun AppCompatActivity.isCurrentFragmentTop(fragmentTag: String, id: Int): Boolean {
    val fragmentManager = this.supportFragmentManager
    val currentFragment = fragmentManager.findFragmentById(id)
    return currentFragment?.let {
        currentFragment.javaClass.canonicalName == fragmentTag
    } ?: false
}

/**
 * This method pops the backstack till the specified fragment
 * @param activity : Parent activity
 * @param tagname: Fragment Tag Name
 */
fun AppCompatActivity.popToProvidedFragment(tagname: String) {
    try {
        this.supportFragmentManager.popBackStackImmediate(tagname, 0)
    } catch (e: IndexOutOfBoundsException) {
        Timber.log(Log.ASSERT, e.message)
    } catch (e: NullPointerException) {
        Timber.log(Log.ASSERT, e.message)
    }
}

/**
 * This method clears the whole backstack including the current fragment
 * @param activity: Parent activity
 */
fun AppCompatActivity.clearBackStackInclusive() {
    if (this.supportFragmentManager.backStackEntryCount == 0)
        return
    val entry = this.supportFragmentManager.getBackStackEntryAt(
        0
    )
    this.supportFragmentManager.popBackStack(
        entry.id,
        FragmentManager.POP_BACK_STACK_INCLUSIVE
    )
    this.supportFragmentManager.executePendingTransactions()
}

/**
 * This method pops the immediate fragment
 */
fun AppCompatActivity.popBackStack() {
    this.supportFragmentManager.popBackStack()
}

/**
 * This method pops the immediate fragment
 */
fun AppCompatActivity.popBackStackImmediate() {
    this.supportFragmentManager.popBackStackImmediate()
}

/**
 * This method pops the back stack by the number of times specified as count
 */
fun AppCompatActivity.popBackStackImmediateByCount(count: Int) {
    for (i in 0 until count)
        this.supportFragmentManager.popBackStackImmediate()
}

/**
 * This method pops the back stack by the number of times specified as count
 */
fun AppCompatActivity.popBackStackByCount(count: Int) {
    for (i in 0 until count)
        this.supportFragmentManager.popBackStack()
}

fun FragmentManager.findFragmentByTag(fragmentTag: String?): Fragment? {
    return this.findFragmentByTag(fragmentTag)
}

fun FragmentActivity.backStackContainsFragment(nameAtStack: String): Boolean {
    for (entry in 0 until supportFragmentManager.backStackEntryCount) {
        if (supportFragmentManager.getBackStackEntryAt(entry).name == nameAtStack) return true
    }
    return false
}

private fun applyFragmentTransition(
    animationType: Int,
    transaction: FragmentTransaction,
    addToBackStack: Boolean,
    fragment: Fragment
) {
    when (animationType) {
        TRANSITION_POP -> transaction.setCustomAnimations(
            R.anim.anim_enter,
            R.anim.anim_exit,
            R.anim.anim_pop_enter,
            R.anim.anim_pop_exit
        )
        TRANSITION_PUSH_TO_STACK -> transaction.setCustomAnimations(
            R.anim.slide_up, R.anim.anim_frag_fade_out, R.anim.anim_frag_fade_in, R.anim.slide_down
        )
        TRANSITION_FADE_IN_OUT -> transaction.setCustomAnimations(
            R.anim.anim_frag_fade_in, R.anim.anim_frag_fade_out,
            R.anim.anim_frag_fade_in, R.anim.anim_frag_fade_out
        )
        TRANSITION_SLIDE_LEFT_RIGHT -> transaction.setCustomAnimations(
            R.anim.slide_in_from_rigth, R.anim.slide_out_to_left,
            R.anim.slide_in_from_left, R.anim.slide_out_to_right
        )
        TRANSITION_FADE_IN_POP_OUT -> transaction.setCustomAnimations(
            R.anim.anim_frag_fade_in,
            R.anim.slide_out_to_left
        )

        TRANSITION_SLIDE_LEFT_RIGHT_WITHOUT_EXIT -> transaction.setCustomAnimations(
            R.anim.slide_in_from_rigth, 0
        )
        TRANSITION_NONE -> transaction.setCustomAnimations(0, 0)
    }

    if (addToBackStack)
        transaction.addToBackStack(fragment.javaClass.canonicalName)
}