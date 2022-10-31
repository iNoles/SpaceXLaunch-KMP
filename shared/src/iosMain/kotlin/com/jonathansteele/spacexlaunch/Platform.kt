package com.jonathansteele.spacexlaunch

actual fun log(tag: String, message: String, exception: Throwable?) {
    println("$tag : $message")
    exception?.let {
        println(exception)
    }
}
