package com.cooper.kotlinbasic.kotlin_tour.classes.instance

class Contact(val id: Int, var email: String = "example@gmail.com") {

    fun printId() {
        println(id)
    }
}
