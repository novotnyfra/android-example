package com.example.androidsample.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidsample.AppParameters
import com.example.androidsample.data.model.User


@Database(entities = [(User::class)], version = 1)
@TypeConverters(AppTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    // DAOs

    abstract fun userDao(): UserDao



    companion object {
        fun buildDatabase(context: Context): AppDatabase
                = Room.databaseBuilder(context.applicationContext,
            AppDatabase::class.java, AppParameters.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

}
