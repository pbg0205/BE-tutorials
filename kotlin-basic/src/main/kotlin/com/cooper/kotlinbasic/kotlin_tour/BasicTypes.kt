package com.cooper.kotlinbasic.kotlin_tour

/**
 * Kotlin's ability to infer the data type is called type inference. customers is assigned an integer value.
 * From this, Kotlin infers that customers has numerical data type: Int
 * - https://kotlinlang.org/docs/kotlin-tour-basic-types.html
 */
fun main() {
    variable()
    value()
}

private fun value() {
    // Variable declared without initialization
    val d: Int

    // Variable initialized
    d = 3

    // Variable explicitly typed and initialized
    val e: String = "hello"

    // Variables can be read because they have been initialized
    println(d) // 3
    println(e) // hello
}

private fun variable() {
    var customers = 10

    // Some customers leave the queue
    customers = 8

    customers = customers + 3 // Example of addition: 11
    customers += 7            // Example of addition: 18
    customers -= 3            // Example of subtraction: 15
    customers *= 2            // Example of multiplication: 30
    customers /= 3            // Example of division: 10

    println(customers) // 10
}

