package com.cooper.kotlinbasic.kotlin_tour.function

fun main() {
    // Function called with both parameters
    printMessageWithPrefix2("Hello", "Log") // [Log] Hello

    // Function called only with message parameter
    printMessageWithPrefix2("Hello") // [Info] Hello

    printMessageWithPrefix2(prefix = "Log", message = "Hello") // [Log] Hello
}

fun printMessageWithPrefix2(message: String, prefix: String = "Info") {
    println("[$prefix] $message")
}
