package com.example.androidsample.data.resource

import androidx.annotation.IntDef

// a generic class that describes a data with a status
class Resource<T>
private constructor(@Status val status: Int, val data: T?, val message: String? = null) {

    companion object {

        const val SUCCESS = 0
        const val ERROR = 1
        const val LOADING = 2

        @IntDef(SUCCESS, ERROR, LOADING)
        @Retention(AnnotationRetention.SOURCE)
        annotation class Status

        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}
