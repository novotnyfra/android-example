package com.example.androidsample.data.local

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


object AppTypeConverters {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // converters for MutableList<String>
    @TypeConverter
    @JvmStatic
    fun restoreStringsList(listOfString: String): MutableList<String>?
            = moshi.adapter<MutableList<String>>(Object::class.java).fromJson(listOfString)

    @TypeConverter
    @JvmStatic
    fun saveStringsList(listOfString: MutableList<String>): String {
        val jsonAdapter = moshi.adapter<MutableList<String>>(Object::class.java)
        return jsonAdapter.toJson(listOfString)
    }

}
