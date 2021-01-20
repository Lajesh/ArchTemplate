package com.template.cleanapp.di.viewmodel

import com.template.cleanapp.presentation.view.fragment.login.LoginViewModel
import com.template.cleanapp.presentation.viewmodel.EmptyViewModel
import com.template.cleanapp.presentation.viewmodel.SharedViewModel
import com.template.cleanapp.presentation.viewmodel.ToolbarPropertyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Viewmodel Module
 * Created by Lajesh Dineshkumar on 10/30/2019.
 * Company: Nagarro Middle East
 * Email: lajesh.dineshkumar@nagarro.com
 */
val viewModelModule = module {

    viewModel {
        EmptyViewModel()
    }

    viewModel {
        SharedViewModel(stringResolver = get())
    }

    viewModel {
        LoginViewModel(stringResolver = get(), authUseCase = get(), fitnessDataUseCase = get())
    }

    viewModel {
        ToolbarPropertyViewModel()
    }
}
