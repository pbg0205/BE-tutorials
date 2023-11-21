package com.cooper.kotlinbasic.kotlin_tour.classes.instance

fun main() {
//    val contact = Contact(1, "mary@gmail.com")
    val contact = Contact(1, "mary@gmail.com")

    // Prints the value of the property: email
    println(contact.email) // mary@gmail.com

    // Updates the value of the property: email
    contact.email = "jane@gmail.com"

    // Prints the new value of the property: email
    println(contact.email) // jane@gmail.com

    contact.printId()
}
