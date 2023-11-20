package com.cooper.kotlinbasic.kotlin_tour.function

fun main() {
    println(sum1(1, 2)) // 3
    println(sum2(1, 2)) // 3
}

fun sum1(x: Int, y: Int): Int {
    return x + y
}

fun sum2(x: Int, y: Int) = x + y
