package com.cooper.kotlinbasic.kotlin_tour.function

fun main() {
    // Uses named arguments with swapped parameter order
    printMessageWithPrefix2(prefix = "Log", message = "Hello") // [Log] Hello
}

fun printMessageWithPrefix(message: String, prefix: String) {
    println("[$prefix] $message")
}
