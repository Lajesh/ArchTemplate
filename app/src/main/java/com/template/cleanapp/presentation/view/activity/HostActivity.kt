package com.template.cleanapp.presentation.view.activity

import android.os.Bundle
import com.template.cleanapp.R
import com.template.cleanapp.BR
import com.template.cleanapp.databinding.ActivityMainBinding
import com.template.cleanapp.presentation.viewmodel.EmptyViewModel
import com.template.cleanapp.utils.NavUtil

class HostActivity : BaseActivity<EmptyViewModel, ActivityMainBinding>(EmptyViewModel::class){
    override val layoutRes: Int
        get() = R.layout.activity_main

    override val bindingVariable: Int
        get() = BR.viewModel

    override fun getViewModel(): Class<EmptyViewModel> {
        return EmptyViewModel::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavUtil.pushLoginScreen(supportFragmentManager)
    }

}