package com.example.androidexample.util.livedata

import androidx.lifecycle.Observer

/**
 * An [Observer] for [LiveEvent]s, simplifying the pattern of checking if the [LiveEvent]'s content has
 * already been handled.
 *
 * [onEventUnhandledContent] is *only* called if the [LiveEvent]'s contents has not been handled.
 */
class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<LiveEvent<T>> {
    override fun onChanged(event: LiveEvent<T>?) {
        event?.getContentIfNotHandled()?.let(onEventUnhandledContent)
    }
}

class EventObserverEverytime<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<LiveEvent<T>> {
    override fun onChanged(event: LiveEvent<T>?) {
        event?.peekContent()?.let(onEventUnhandledContent)
    }
}
