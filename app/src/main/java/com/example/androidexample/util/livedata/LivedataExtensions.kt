package com.example.androidexample.util.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeOnce(observer: Observer<T>) {
    observeForever(object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun <T> MutableLiveData<LiveEvent<T>>.event(value: T) = apply { postValue(LiveEvent(value)) }

fun MutableLiveData<Boolean>.toTrue() { value = true }
fun MutableLiveData<Boolean>.toFalse() { value = false }
