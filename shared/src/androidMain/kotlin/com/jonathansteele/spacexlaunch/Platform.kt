package com.jonathansteele.spacexlaunch

import android.util.Log

actual fun log(tag: String, message: String, exception: Throwable?) {
    exception?.let {
        Log.e(tag, message, exception)
    } ?: run {
        Log.d(tag, message)
    }
}