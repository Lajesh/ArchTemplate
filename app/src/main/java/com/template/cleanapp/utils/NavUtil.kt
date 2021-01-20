package com.template.cleanapp.utils

import androidx.fragment.app.FragmentManager
import com.template.cleanapp.presentation.view.fragment.login.LoginFragment
import com.template.cleanapp.utils.extensions.FragmentAnimation
import com.template.cleanapp.utils.extensions.replaceFragment
import com.template.cleanapp.R

/*******
 * Keep all navigation methods here
 * Author: Lajesh Dineshkumar
 * Created on: 16/10/2020
 * Modified on: 16/10/2020
 ********/
object NavUtil {

    fun pushLoginScreen(fragmentManager: FragmentManager){
        fragmentManager.replaceFragment(
            LoginFragment(),
            R.id.fragmentContainer, false, FragmentAnimation.TRANSITION_NONE
        )
    }

}