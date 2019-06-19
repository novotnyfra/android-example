package com.example.androidsample.di

import com.example.androidsample.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

    viewModel { MainViewModel() }

}
