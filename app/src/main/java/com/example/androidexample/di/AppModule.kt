package com.example.androidexample.di

import com.example.androidexample.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

    viewModel { MainViewModel() }

}
