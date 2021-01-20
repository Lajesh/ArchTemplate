package com.template.cleanapp.presentation.view.fragment.login

import androidx.lifecycle.Observer
import com.template.cleanapp.R
import com.template.cleanapp.BR
import com.template.cleanapp.contract.SubscriptionContract
import com.template.cleanapp.databinding.FragmentLoginBinding
import com.template.cleanapp.presentation.view.fragment.BaseFragment

/*******
 * Login Fragment
 * Author: Lajesh Dineshkumar
 * Created on: 16/10/2020
 * Modified on: 16/10/2020
 ********/
class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>(LoginViewModel::class),
    SubscriptionContract {
    override val layoutRes: Int
        get() = R.layout.fragment_login

    override val bindingVariable: Int
        get() = BR.viewModel

    override val subscriptionContract: SubscriptionContract?
        get() = this

    override fun subscribeNavigationEvent() {
        super.subscribeNavigationEvent()
        viewModel.loginSuccessEvent.observe(this, Observer {
            activity?.supportFragmentManager?.let {
               // TODO
            }

        })
    }

    override fun unsubscribe() {
        super.unsubscribe()
        viewModel.loginSuccessEvent.removeObservers(this)
    }
}