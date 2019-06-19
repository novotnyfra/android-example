package com.example.androidexample.data.local

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


object AppTypeConverters {

    val moshi = Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    // converters for MutableList<String>
    @TypeConverter
    @JvmStatic
    fun restoreStringsList(listOfString: String): MutableList<String>?
            = moshi.adapter(mutableListOf<String>()::class.java).fromJson(listOfString)

    @TypeConverter
    @JvmStatic
    fun saveStringsList(listOfString: MutableList<String>): String {
        val jsonAdapter: JsonAdapter<MutableList<String>> = moshi.adapter(Types.newParameterizedType(mutableListOf<String>()::class.java))
        return jsonAdapter.toJson(listOfString)
    }

}
