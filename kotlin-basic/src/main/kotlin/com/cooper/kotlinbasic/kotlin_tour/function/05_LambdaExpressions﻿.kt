package com.cooper.kotlinbasic.kotlin_tour.function

fun main() {
    // 1. simple lambda expression
    println(uppercaseString("hello")) // HELLO
    println({ string: String -> string.uppercase() }("hello")) // HELLO

    // 2. 변수에 람다 식을 할당
    val upperCaseString01 = { string: String -> string.uppercase() }
    println(upperCaseString01("hello")) // HELLO

    // 3. filter() 함수
    val numbers = listOf(1, -2, 3, -4, 5, -6)
    val positives = numbers.filter { x -> x > 0 }
    val negatives = numbers.filter { x -> x < 0 }
    println(positives) // [1, 3, 5]
    println(negatives) // [-2, -4, -6]

    // 4. map() 함수
    val doubled = numbers.map { x -> x * 2 }
    println(doubled) // [2, -4, 6, -8, 10, -12]

    val tripled = numbers.map { x -> x * 3 }
    println(tripled) // [3, -6, 9, -12, 15, -18]

    // 5. 함수 타입 선언
    val upperCaseString02: (String) -> String = { string -> string.uppercase() }
    println(upperCaseString02("hello")) // HELLO

    // 6. 함수 유형을 선언 : 컴파일러가 반환된 람다 표현식이 어떤 유형인지 이해하기 위함
    val timesInMinutes = listOf(2, 10, 15, 1)
    val min2sec = toSeconds("minute")
    val totalTimeInSeconds = timesInMinutes.map(min2sec).sum()
    println("Total time is $totalTimeInSeconds secs")  // Total time is 1680 secs

    // 7. fold() : 초기값과 연산 방식을 전달받는 함수
    // The initial value is zero.
    println(listOf(1, 2, 3).fold(0, { x, item -> x + item })) // 6
    println(listOf(1, 2, 3).fold(0) { x, item -> x + item })  // 6



}

fun uppercaseString(string: String): String {
    return string.uppercase()
}

fun toSeconds(time: String): (Int) -> Int = when (time) {
    "hour" -> { value -> value * 60 * 60 }
    "minute" -> { value -> value * 60 }
    "second" -> { value -> value }
    else -> { value -> value }
}
