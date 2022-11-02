package com.jonathansteele.spacexlaunch

actual fun log(tag: String, message: String, exception: Throwable?) {
    println(message)
    println(exception)
}