package com.example.androidexample.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


//@Database(entities = [], version = 1)
//@TypeConverters(AppTypeConverters::class)
//abstract class AppDatabase : RoomDatabase() {
//
//    // DAOs
//
//
//
//    companion object {//
//        fun buildDatabase(context: Context): AppDatabase
//                = Room.databaseBuilder(context.applicationContext,
//                AppDatabase::class.java, DATABASE_NAME)
//                .fallbackToDestructiveMigration()
//                .build()
//    }
//}