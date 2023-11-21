package com.cooper.kotlinbasic.kotlin_tour.classes.data

fun main() {
    /**
     * create instance
     */
    val user1 = User("Alex", 1)
    println(user1)

    val user2 = User(id=1, name="Alex")
    println(user2)


    val user = User("Alex", 1)
    val secondUser = User("Alex", 1)
    val thirdUser = User("Max", 2)
    /**
     * compare instances
     */

    // Compares user to second user
    println("user == secondUser: ${user == secondUser}") // user == secondUser: true

    // Compares user to third user
    println("user == thirdUser: ${user == thirdUser}") // user == thirdUser: false

    /**
     * copy instance
     */
    // Creates an exact copy of user
    println(user.copy()) // User(name=Alex, id=1)

    // Creates a copy of user with name: "Max"
    println(user.copy("Max")) // User(name=Max, id=1)

    // Creates a copy of user with id: 3
    println(user.copy(id = 3)) // User(name=Alex, id=3)

}
