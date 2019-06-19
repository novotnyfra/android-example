package com.example.androidsample.di

import android.content.Context
import com.example.androidsample.data.AppRepository
import com.example.androidsample.data.AppRepositoryImpl
import com.example.androidsample.data.local.AppDatabase
import com.example.androidsample.data.local.SharedPrefRepo
import com.example.androidsample.data.local.SharedPrefRepoImpl
import org.koin.dsl.module.module

val dataModule = module {

    // App Database
    single { provideAppDatabase(get()) }

    // User DAO
    single { provideUserDao(get()) }

    // User Repository
    bean { AppRepositoryImpl(get(), get(), get(), get()) as AppRepository }

    // Shared Preferences Repository
    bean { SharedPrefRepoImpl(get()) as SharedPrefRepo }

}

fun provideAppDatabase(context: Context)
        = AppDatabase.buildDatabase(context)

fun provideUserDao(database: AppDatabase)
        = database.userDao()
